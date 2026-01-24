package model;
public record Setor(Long id, String nome) {

    public Setor{
        if (id == null || nome == null) {
            throw new IllegalArgumentException("Dados do setor inválidos");
        }
    }
}

// get = id() e nome()