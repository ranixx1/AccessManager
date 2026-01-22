import enums.Role;

public class Usuario {
    private final Long id;
    private final String matricula;

    private String nome;
    private Role role;
    private boolean ativo;

    public Usuario(Long id, String matricula, String nome, Role role) {
        this.id = id;
        this.matricula = matricula;
        this.nome = nome;
        this.role = role;
        this.ativo = true;
    }
}
