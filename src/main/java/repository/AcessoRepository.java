package repository;

import model.Acesso;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class AcessoRepository {

    private static final String FILE = "csv/acessos.csv";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static void save(List<Acesso> acessos) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(FILE))) {

            pw.println("usuario_id,setor_id,horario_entrada,horario_saida,acesso_permitido");

            for (Acesso a : acessos) {
                pw.printf(
                        "%d,%d,%s,%s,%b%n",
                        a.usuario_id(),
                        a.setor_id(),
                        a.entrada().format(FORMATTER),
                        a.saida() != null ? a.saida().format(FORMATTER) : "",
                        a.permitido()
                );
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao salvar acessos", e);
        }
    }

    // 🔥 ESSE MÉTODO ESTAVA FALTANDO
    public static List<Acesso> load() {
        List<Acesso> acessos = new ArrayList<>();

        File file = new File(FILE);
        if (!file.exists()) {
            return acessos;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(FILE))) {

            br.readLine(); // pula cabeçalho
            String linha;

            while ((linha = br.readLine()) != null) {
                String[] dados = linha.split(",");

                Long usuarioId = Long.parseLong(dados[0]);
                Long setorId = Long.parseLong(dados[1]);

                LocalDateTime entrada =
                        LocalDateTime.parse(dados[2], FORMATTER);

                LocalDateTime saida = dados[3].isEmpty()
                        ? null
                        : LocalDateTime.parse(dados[3], FORMATTER);

                boolean permitido = Boolean.parseBoolean(dados[4]);

                acessos.add(new Acesso(
                        usuarioId,
                        setorId,
                        entrada,
                        saida,
                        permitido
                ));
            }

        } catch (Exception e) {
            throw new RuntimeException("Erro ao carregar acessos", e);
        }

        return acessos;
    }
}
