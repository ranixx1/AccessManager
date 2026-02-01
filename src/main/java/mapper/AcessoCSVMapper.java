package mapper;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import model.Acesso;

public class AcessoCSVMapper {

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static List<Acesso> load(String path) {

        List<Acesso> acessos = new ArrayList<>();

        try {
            List<String> linhas = Files.readAllLines(Path.of(path));

            // pula o cabeçalho
            for (int i = 1; i < linhas.size(); i++) {

                String linha = linhas.get(i);
                if (linha.isBlank()) continue;

                String[] c = linha.split(",");

                Long usuarioId = Long.parseLong(c[0]);
                Long setorId = Long.parseLong(c[1]);

                LocalDateTime entrada =
                        LocalDateTime.parse(c[2], FORMATTER);

                LocalDateTime saida = null;
                if (!c[3].isBlank()) {
                    saida = LocalDateTime.parse(c[3], FORMATTER);
                }

                boolean permitido =
                        Boolean.parseBoolean(c[4]);

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
