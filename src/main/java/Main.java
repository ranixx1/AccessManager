import java.util.*;

import mapper.*;
import model.*;
import enums.*;
import service.*;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // ===== LOAD CSV =====
        List<Usuario> usuarios = UsuarioCSVMapper.load("csv/usuarios.csv");
        List<Permissao> permissoes = PermissaoCSVMapper.load("csv/permissoes.csv");
        List<Acesso> acessosCSV = AcessoCSVMapper.load("csv/acessos.csv");
        // ===== SERVICES =====
        UsuarioService usuarioService = new UsuarioService(usuarios);
        AcessoService acessoService = new AcessoService(usuarios, permissoes);

        acessoService.carregarHistorico(acessosCSV);

        while (true) {
            System.out.println("\n===== MENU =====");
            System.out.println("1 - Criar usuário");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Desativar usuário");
            System.out.println("4 - Reativar usuário");
            System.out.println("5 - Validar acesso");
            System.out.println("6 - Mostrar histórico de acessos");
            System.out.println("7 - Promover usuário");
            System.out.println("8 - Registrar saída");
            System.out.println("0 - Sair");

            int op = sc.nextInt();
            sc.nextLine();

            try {
                switch (op) {
                    case 1 -> {
                        System.out.print("Nome: ");
                        String nome = sc.nextLine();
                        System.out.print("Role (ADMIN, MANAGER, EMPLOYEE, VISITOR): ");
                        Role role = Role.valueOf(sc.next().toUpperCase());
                        usuarioService.criarUsuario(nome, role);
                        System.out.println("Usuário criado com sucesso.");
                    }
                    case 2 -> {
                        usuarioService.imprimirAll();
                    }

                    case 3 -> {
                        System.out.print("ID do usuário: ");
                        usuarioService.desativarUsuario(sc.nextLong());
                        System.out.println("Usuário desativado.");
                    }

                    case 4 -> {
                        System.out.print("ID do usuário: ");
                        usuarioService.reativarUsuario(sc.nextLong());
                        System.out.println("Usuário reativado.");
                    }

                    case 5 -> {
                        System.out.print("ID do usuário: ");
                        Long usuarioId = sc.nextLong();

                        System.out.print("ID do setor: ");
                        Long setorId = sc.nextLong();

                        boolean permitido = acessoService.validar(usuarioId, setorId);

                        System.out.println(
                                permitido ? "Acesso permitido" : "Acesso negado");

                    }

                    case 6 -> {
                        List<Acesso> hist = acessoService.getHistorico();
                        if (hist.isEmpty()) {
                            System.out.println("Nenhum acesso registrado.");
                        } else {
                            hist.forEach(System.out::println);
                        }
                    }
                    case 7 -> {
                        System.out.print("ID do usuário: ");
                        Long usu = sc.nextLong();
                        System.out.print("novaRole (ADMIN, MANAGER, EMPLOYEE, VISITOR): ");
                        Role novaRole = Role.valueOf(sc.next().toUpperCase());
                        usuarioService.promover(usu, novaRole);
                    }
                    case 8 -> {
                        System.out.print("ID do usuário: ");
                        Long usuarioId = sc.nextLong();

                        System.out.print("ID do setor: ");
                        Long setorId = sc.nextLong();

                        acessoService.registrarSaida(usuarioId, setorId);
                        System.out.println("Saída registrada com sucesso.");
                    }

                    case 0 -> {
                        System.out.println("Encerrando...");
                        return;
                    }

                    default -> System.out.println("Opção inválida.");
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }
    }
}