package model;

import java.time.LocalTime;

public record Acesso(
    Long usuario_id,
    Long setor_id,
    LocalTime entrada,
    LocalTime saida,
    boolean permitido
) {
    public Acesso{
        if(usuario_id == null || setor_id == null || entrada == null){
            throw new IllegalArgumentException("Acesso inválido");
        }
        if(saida != null && saida.isBefore(entrada)){
            throw new IllegalArgumentException("Saída antes da entrada");
        }
    }
}
