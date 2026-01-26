package repository;

import model.Usuario;
import java.io.*;
import java.util.*;


public class UsuarioRepository {
    private static final String FILE = "csv/usuarios.csv";

    public static void save(List<Usuario>usuarios){
        try (PrintWriter pw  = new PrintWriter(new FileWriter(FILE))) { 
            pw.println("id,matricula,nome,role,ativo");

            for(Usuario u : usuarios){
                pw.printf(
                "%d,%s,%s,%s,%b%n",
                u.getId(),
                u.getMatricula(),
                u.getNome(),
                u.getRole(),
                u.isAtivo()
            );
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao criar usuário");
        }
    }
}


// EDITAR CSV