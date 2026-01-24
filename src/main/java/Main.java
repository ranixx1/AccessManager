import java.time.LocalTime;
import java.util.*;

import mapper.*;
import model.*;
import service.AcessoService;

public class Main {

    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {

        // ===== CARGA DOS DADOS =====
        List<Usuario> usuarios = UsuarioCSVMapper.load("csv/usuarios.csv");
        List<Setor> setores = SetorCSVMapper.load("csv/setores.csv");
        List<Permissao> permissoes = PermissaoCSVMapper.load("csv/permissoes.csv");

        AcessoService service = new AcessoService(usuarios, permissoes);

        int opcao;
        do {
            System.out.println("\n=== CONTROLE DE ACESSO ===");
            System.out.println("1 - Tentar acesso");
            System.out.println("2 - Listar usuários");
            System.out.println("3 - Relatório");
            System.out.println("0 - Sair");
            System.out.print("Escolha: ");

            opcao = sc.nextInt();

            switch (opcao) {
                case 1 -> tentarAcesso(service);
                case 2 -> listarUsuarios(usuarios);
                case 3 -> mostrarRelatorio(service);
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opção inválida");
            }

        } while (opcao != 0);
    }

    // ===== OPÇÃO 1 =====
    private static void tentarAcesso(AcessoService service) {
        System.out.print("ID do usuário: ");
        Long usuarioId = sc.nextLong();

        System.out.print("ID do setor: ");
        Long setorId = sc.nextLong();

        System.out.print("Hora (HH:mm): ");
        LocalTime hora = LocalTime.parse(sc.next());

        boolean acesso = service.validar(usuarioId, setorId, hora);

        System.out.println(
            acesso ? "ACESSO LIBERADO ✅" : "ACESSO NEGADO ❌"
        );
    }

    // ===== OPÇÃO 2 =====
    private static void listarUsuarios(List<Usuario> usuarios) {
        System.out.println("\n--- USUÁRIOS ---");
        usuarios.forEach(System.out::println);
    }

    // ===== OPÇÃO 3 =====
    private static void mostrarRelatorio(AcessoService service) {
        List<Acesso> historico = service.getHistorico();

        long total = historico.size();
        long liberados = historico.stream().filter(Acesso::permitido).count();
        long negados = total - liberados;

        System.out.println("\n--- RELATÓRIO ---");
        System.out.println("Total de tentativas: " + total);
        System.out.println("Acessos liberados: " + liberados);
        System.out.println("Acessos negados: " + negados);
    }
}
