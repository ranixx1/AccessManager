package model;

import java.time.LocalDateTime;

public record Acesso(
        Long usuario_id,
        Long setor_id,
        LocalDateTime entrada,
        LocalDateTime saida,
        boolean permitido) {
    public Acesso {
        if (usuario_id == null || setor_id == null || entrada == null) {
            throw new IllegalArgumentException("Acesso inválido");

        }
        if (saida != null && saida.isBefore(entrada)) {
            throw new IllegalArgumentException("Saída antes da entrada");
        }
    }
}

/*

        if (permitido && motivoRejeicao != null) {
            throw new IllegalArgumentException("Acesso permitido não deve ter motivo de rejeição");
        }

        if (!permitido && motivoRejeicao == null) {
            throw new IllegalArgumentException("Acesso rejeitado deve ter motivo");
        }


*/
