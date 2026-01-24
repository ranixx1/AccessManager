package model;
import enums.Role;

public class Usuario {
    private final Long id;
    private final String matricula;

    private String nome;
    private Role role;
    private boolean ativo;

    public Usuario(Long id, String matricula, String nome, Role role, boolean ativo) {
        if (id == null || matricula == null || nome == null || role == null) {
            throw new IllegalArgumentException("Dados do usuário inválidos");
        }
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
        this.role = role;
        this.ativo = ativo;
    }

    public Long getId() {
        return id;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getNome() {
        return nome;
    }

    public Role getRole() {
        return role;
    }

    public void setNome(String nome) {
        if (nome == null) {
            throw new IllegalArgumentException("Nome inválido");
        }
        this.nome = nome;
    }

    public void promover(Role novoRole) {
        if (novoRole == null) {
            throw new IllegalArgumentException("Role inválido");
        }
        this.role = novoRole;
    }

    public boolean podeAcessarSistema() {
        return ativo;
    }
    public boolean isAdmin(){
        return role == Role.ADMIN;
    }
    public boolean isAtivo() {
        return ativo;
    }

    public void desativar() {
        this.ativo = false;
    }

    public void ativar() {
        this.ativo = true;
    }

    public void bloquear() {
        desativar();
    }

    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", matricula='" + matricula + '\'' +
                ", nome='" + nome + '\'' +
                ", role=" + role +
                ", ativo=" + ativo +
                '}';
    }

}
