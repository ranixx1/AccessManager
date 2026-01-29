package service;

import java.time.LocalTime;
import java.time.LocalDateTime;

import java.util.*;

import model.*;

public class AcessoService {

    private final List<Usuario> usuarios;
    private final List<Permissao> permissoes;

    // histórico vive aqui
    private final List<Acesso> historico = new ArrayList<>();

    public AcessoService(List<Usuario> usuarios, List<Permissao> permissoes) {
        this.usuarios = usuarios;
        this.permissoes = permissoes;
    }

    public boolean validar(Long usuarioId, Long setorId) {
        LocalDateTime agora = LocalDateTime.now();
        LocalTime hora = agora.toLocalTime();

        boolean permitido = false;

        Usuario usuario = usuarios.stream()
                .filter(u -> u.getId().equals(usuarioId))
                .findFirst()
                .orElse(null);

        if (usuario != null && usuario.isAtivo()) {

            // ADMIN tem acesso total 24h
            if (usuario.isAdmin()) {
                permitido = true;
            } else {
                permitido = permissoes.stream()
                        .anyMatch(p -> p.getUsuarioId().equals(usuarioId) &&
                                p.getSetorId().equals(setorId) &&
                                p.podeEntrar() &&
                                !hora.isBefore(p.getHorarioInicio()) &&
                                !hora.isAfter(p.getHorarioFim()));
            }
        }

        // REGISTRA A TENTATIVA
        historico.add(new Acesso(
                usuarioId,
                setorId,
                agora,
                null,
                permitido));

        return permitido;
    }

    public void carregarHistorico(List<Acesso> acessos) {
        historico.addAll(acessos);
    }

    // acesso controlado ao histórico
    public List<Acesso> getHistorico() {
        return Collections.unmodifiableList(historico);
    }
}
