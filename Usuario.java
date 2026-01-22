import enums.Role;

public class Usuario {
    private final Long id;
    private final String matricula;

    private String nome;
    private Role role;
    private boolean ativo;

    public Usuario(Long id, String matricula, String nome, Role role) {
        if (id == null || matricula == null || nome == null || role == null) {
            throw new IllegalArgumentException("Dados do usuário inválidos");
        }
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
        this.role = role;
        this.ativo = true;
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
            throw new IllegalArgumentException("Nome inválido");
        }
        this.role = novoRole;
    }
}