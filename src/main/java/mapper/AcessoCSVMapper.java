package mapper;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

import model.Acesso;

public class AcessoCSVMapper {

    public static List<Acesso> load(String path) {

        List<Acesso> acessos = new ArrayList<>();

        try {
            List<String> linhas = Files.readAllLines(Path.of(path));

            // pula o cabeçalho
            for (int i = 1; i < linhas.size(); i++) {

                String linha = linhas.get(i);
                if (linha.isBlank()) continue;

                String[] c = linha.split(",");

                Long usuarioId = Long.valueOf(c[1]);
                Long setorId = Long.valueOf(c[2]);

                LocalDateTime entrada =
                        LocalDateTime.parse(c[3]);

                LocalDateTime saida = null;
                if (c.length > 4 && !c[4].isBlank()) {
                    saida = LocalDateTime.parse(c[4]);
                }

                boolean permitido = Boolean.parseBoolean(c[5]);

                acessos.add(new Acesso(
                        usuarioId,
                        setorId,
                        entrada,
                        saida,
                        permitido
                ));
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler acessos.csv", e);
        }

        return acessos;
    }
}
