package service;

import java.util.HashSet;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Random;
import java.util.Set;
import repository.*;

import model.*;
import enums.Role;

public class UsuarioService {

    private final List<Usuario> usuarios;
    private final Set<String> matriculasUsadas = new HashSet<>();
    private long proximoId;

    public UsuarioService(List<Usuario> usuarios) {
        this.usuarios = usuarios;

        long max = 0;
        for (Usuario u : usuarios) {
            matriculasUsadas.add(u.getMatricula());
            if (u.getId() > max) {
                max = u.getId();
            }
        }
        this.proximoId = max + 1;
    }

    private Long gerarProximoId() {
        return proximoId++;
    }

    private String gerarMatricula() {
        Random r = new Random();
        String mat;
        do {
            mat = String.valueOf(100000 + r.nextInt(900000));
        } while (matriculasUsadas.contains(mat));

        matriculasUsadas.add(mat);
        return mat;
    }

    // CRIAR
    public void criarUsuario(String nome, Role role) {

        Long id = gerarProximoId();
        String matricula = gerarMatricula();

        usuarios.add(new Usuario(
                id,
                matricula,
                nome,
                role,
                true));

        UsuarioRepository.save(usuarios);
    }

    // READ
    public Usuario buscarPorId(Long id) {
        return usuarios.stream()
                .filter(u -> u.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void imprimirAll() {
        if (usuarios.isEmpty()) {
            System.out.println("Lista de usuários está vazia");
            return;
        }
        for (Usuario u : usuarios) {
            System.out.println(u);
        }

    }

    public void desativarUsuario(Long id) {

        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {

                if (!u.isAtivo()) {
                    throw new IllegalStateException("Usuário já está desativado");
                }

                u.desativar(); // 👈 domínio manda
                UsuarioRepository.save(usuarios);
                return;
            }
        }

        throw new NoSuchElementException("Usuário não encontrado");
    }

    public void promover(Long id, Role novoRole) {
        if (novoRole == null) {
            throw new IllegalArgumentException("Role inválido");
        }
        if (id == null) {
            throw new IllegalArgumentException("id inválido");
        }
        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {
                u.promover(novoRole);
            }
        }
        UsuarioRepository.save(usuarios);
        return;

    }

    public void reativarUsuario(Long id) {

        for (Usuario u : usuarios) {
            if (u.getId().equals(id)) {

                if (u.isAtivo()) {
                    throw new IllegalStateException("Usuário já está ativo");
                }

                u.ativar();
                UsuarioRepository.save(usuarios);
                return;
            }
        }

        throw new NoSuchElementException("Usuário não encontrado");
    }

}