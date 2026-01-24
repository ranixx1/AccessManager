package util;


import java.io.*;
import java.util.*;

public class CSVReader {

  public static List<String[]> read(String path) {
        List<String[]> linhas = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String linha;
            boolean header = true;

            while ((linha = br.readLine()) != null) {
                if (header) { // pula cabeçalho
                    header = false;
                    continue;
                }
                linhas.add(linha.split(","));
            }

        } catch (IOException e) {
            throw new RuntimeException("Erro ao ler CSV: " + path, e);
        }

        return linhas;
    }
}
