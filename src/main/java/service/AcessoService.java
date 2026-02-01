package service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import model.Acesso;
import model.Permissao;
import model.Usuario;
import repository.AcessoRepository;

public class AcessoService {

    private final List<Usuario> usuarios;
    private final List<Permissao> permissoes;

    private final List<Acesso> historico = new ArrayList<>();

    public AcessoService(List<Usuario> usuarios, List<Permissao> permissoes) {
        this.usuarios = usuarios;
        this.permissoes = permissoes;

        try {
            historico.addAll(AcessoRepository.load());
        } catch (Exception e) {
        }
    }

    public boolean validar(Long usuarioId, Long setorId) {

        LocalDateTime agora = LocalDateTime.now();
        LocalTime horaAtual = agora.toLocalTime();

        boolean permitido = false;

        Usuario usuario = usuarios.stream()
                .filter(u -> u.getId().equals(usuarioId))
                .findFirst()
                .orElse(null);

        if (usuario != null && usuario.isAtivo()) {

            // ADMIN entra sempre
            if (usuario.isAdmin()) {
                permitido = true;
            } else {
                permitido = permissoes.stream()
                        .anyMatch(p -> p.getUsuarioId().equals(usuarioId) &&
                                p.getSetorId().equals(setorId) &&
                                p.podeEntrar() &&
                                !horaAtual.isBefore(p.getHorarioInicio()) &&
                                !horaAtual.isAfter(p.getHorarioFim()));
            }
        }

        // cria o acesso
        Acesso acesso = new Acesso(
                usuarioId,
                setorId,
                agora,
                null,
                permitido);

        historico.add(acesso);

        // persiste no CSV
        AcessoRepository.save(historico);

        return permitido;
    }

    public void registrarSaida(Long usuarioId, Long setorId) {

        LocalDateTime agora = LocalDateTime.now();

        // encontra o último acesso aberto permitido
        Optional<Acesso> acessoAberto = historico.stream()
                .filter(a -> a.usuario_id().equals(usuarioId) &&
                        a.setor_id().equals(setorId) &&
                        a.saida() == null &&
                        a.permitido())
                .reduce((first, second) -> second); // último

        if (acessoAberto.isEmpty()) {
            throw new IllegalStateException("Nenhum acesso aberto encontrado");
        }

        Acesso antigo = acessoAberto.get();

        // remove o acesso antigo
        historico.remove(antigo);

        // cria o acesso fechado
        Acesso fechado = new Acesso(
                antigo.usuario_id(),
                antigo.setor_id(),
                antigo.entrada(),
                agora,
                antigo.permitido());

        // adiciona o fechado
        historico.add(fechado);

        // salva tudo
        AcessoRepository.save(historico);
    }

    public void carregarHistorico(List<Acesso> acessos) {
        historico.addAll(acessos);
    }

    public List<Acesso> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}
