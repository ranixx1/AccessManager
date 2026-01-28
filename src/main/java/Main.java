import java.util.*;

import mapper.*;
import model.*;
import enums.*;
import service.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        List<Usuario> usuarios = UsuarioCSVMapper.load("csv/usuarios.csv");

        // ===== SERVICE =====
        UsuarioService usuarioService = new UsuarioService(usuarios);
        Long id;
        System.out.println("1 - CRIAR USUARIO");
        System.out.println("2 - DESATIVAR USUARIO");
        System.out.println("3 - REATIVAR USUARIO");
        int opcoes = sc.nextInt();
        sc.nextLine();
        switch (opcoes) {
            case 1:
                String nome = sc.nextLine();
                Role role = Role.valueOf(sc.next().toUpperCase());

                usuarioService.criarUsuario(nome, role);

                System.out.println("Usuário criado com sucesso!");

                break;
            case 2:
                id = sc.nextLong();

                usuarioService.desativarUsuario(id);
                break;
            case 3:

                id = sc.nextLong();

                usuarioService.reativarUsuario(id);
                break;
            default:
                break;
        }
    }
}
