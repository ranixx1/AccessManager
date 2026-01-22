import java.time.LocalTime;

public class Permissoes {
    private final Long id;
    private final Long setor_id;
    private final Long usuario_id;
    private final LocalTime horario_inicio;
    private final LocalTime horario_fim;
    private boolean pode_entrar;

    public Permissoes(Long id, Long setor_id, Long usuario_id, LocalTime horario_inicio,
            LocalTime horario_fim, boolean pode_entrar) {
        if (id == null || setor_id == null || usuario_id == null || horario_inicio == null || horario_fim == null) {
            throw new IllegalArgumentException("Dados de permissão inválidos");
        }
        this.id = id;
        this.setor_id = setor_id;
        this.usuario_id = usuario_id;
        this.horario_inicio = horario_inicio;
        this.horario_fim = horario_fim;
        this.pode_entrar = pode_entrar;
    }

    // getters e setters
    public Long getId() {
        return id;
    }

    public Long getUsuarioId() {
        return usuario_id;
    }

    public Long getSetorId() {
        return setor_id;
    }

    public LocalTime getHorarioInicio() {
        return horario_inicio;
    }

    public LocalTime getHorarioFim() {
        return horario_fim;
    }

    public boolean podeEntrar() {
        return pode_entrar;

    }

    public void liberarAcesso() {
        this.pode_entrar = true;
    }

    public void bloquearAcesso() {
        this.pode_entrar = false;
    }

}
/*
 * - id
 * - usuario_id
 * - setor_id
 * - pode_entrar (true/false)
 * - horario_inicio
 * - horario_fim
 * 
 */