import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PetshopApp {

    private static GerenciadorPetshop petshopService = new PetshopService();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            petshopService.carregarDados();
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao carregar os dados: " + e.getMessage());
        }

        int opcao = 0;
        do {
            exibirMenu();
            try {
                opcao = scanner.nextInt();
            } catch (InputMismatchException e) {
                System.err.println("Entrada inválida. Por favor, digite um número.");
                scanner.nextLine(); // Consome a entrada inválida
                continue;
            }
            scanner.nextLine(); // Consome a nova linha

            switch (opcao) {
                case 1:
                    cadastrarAnimal();
                    break;
                case 2:
                    removerAnimal();
                    break;
                case 3:
                    pesquisarAnimal();
                    break;
                case 4:
                    listarAnimais();
                    break;
                case 5:
                    registrarInternamento();
                    break;
                case 6:
                    registrarPlanoSaude();
                    break;
                case 7:
                    verSuporte();
                    break;
                case 8:
                    salvarDados();
                    break;
                case 9:
                    System.out.println("Saindo do sistema. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 9);
    }

    private static void exibirMenu() {
        System.out.println("\n--- MENU PETSHOP ---");
        System.out.println("1. Cadastrar novo animal");
        System.out.println("2. Remover animal");
        System.out.println("3. Pesquisar animal");
        System.out.println("4. Listar todos os animais");
        System.out.println("5. Registrar internamento");
        System.out.println("6. Registrar plano de saúde");
        System.out.println("7. Suporte");
        System.out.println("8. Salvar dados");
        System.out.println("9. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarAnimal() {
        try {
            System.out.print("Nome do animal: ");
            String nome = scanner.nextLine();
            System.out.print("Espécie: ");
            String especie = scanner.nextLine();
            System.out.print("Raça: ");
            String raca = scanner.nextLine();
            System.out.print("Idade: ");
            int idade = scanner.nextInt();
            scanner.nextLine();

            System.out.print("Nome do dono: ");
            String nomeDono = scanner.nextLine();
            System.out.print("Telefone do dono: ");
            String telefoneDono = scanner.nextLine();
            Dono dono = new Dono(nomeDono, telefoneDono);

            System.out.print("Serviço contratado (ex: Banho, Tosa): ");
            String descricaoServico = scanner.nextLine();
            System.out.print("Valor do serviço: ");
            double valorServico = scanner.nextDouble();
            scanner.nextLine();
            Servico servico = new Servico(descricaoServico, valorServico);

            Animal animal = new Animal(nome, especie, raca, idade, dono, servico);

            petshopService.cadastrarAnimal(animal);

        } catch (InputMismatchException e) {
            System.err.println("Entrada inválida. Tente novamente.");
            scanner.nextLine();
        } catch (AnimalJaCadastradoException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void removerAnimal() {
        System.out.print("Digite o nome do animal para remover: ");
        String nome = scanner.nextLine();
        try {
            petshopService.removerAnimal(nome);
        } catch (AnimalNaoEncontradoException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void pesquisarAnimal() {
        System.out.print("Digite o nome do animal para pesquisar: ");
        String nome = scanner.nextLine();
        try {
            Animal animal = petshopService.pesquisarAnimal(nome);
            System.out.println("Animal encontrado: " + animal);
        } catch (AnimalNaoEncontradoException e) {
            System.err.println(e.getMessage());
        }
    }

    private static void listarAnimais() {
        if (petshopService.listarAnimais().isEmpty()) {
            System.out.println("Não há animais cadastrados.");
        } else {
            System.out.println("\n--- LISTA DE ANIMAIS ---");
            petshopService.listarAnimais().forEach(animal -> {
                System.out.println(animal);
                if (animal.getPlanoSaude() != null) {
                    System.out.println("   Plano de Saúde: " + animal.getPlanoSaude());
                }
                if (!animal.getHistoricoInternamentos().isEmpty()) {
                    System.out.println("   Histórico de Internamentos:");
                    animal.getHistoricoInternamentos().forEach(internamento -> System.out.println("     " + internamento));
                }
                System.out.println();
            });
        }
    }

    private static void registrarInternamento() {
        try {
            System.out.print("Nome do animal: ");
            String nomeAnimal = scanner.nextLine();
            System.out.print("Motivo do internamento: ");
            String motivo = scanner.nextLine();
            System.out.print("Data de entrada (AAAA-MM-DD): ");
            String dataStr = scanner.nextLine();
            LocalDate data = LocalDate.parse(dataStr);
            System.out.print("Observações: ");
            String observacoes = scanner.nextLine();

            Internamento internamento = new Internamento(motivo, data, observacoes);
            petshopService.registrarInternamento(nomeAnimal, internamento);

            System.out.println("Internamento registrado com sucesso!");
        } catch (AnimalNaoEncontradoException | DateTimeParseException e) {
            System.err.println("Erro: " + e.getMessage());
        }
    }

    private static void registrarPlanoSaude() {
        try {
            System.out.print("Nome do animal: ");
            String nomeAnimal = scanner.nextLine();
            System.out.print("Nome do plano: ");
            String nomePlano = scanner.nextLine();
            System.out.print("Número do contrato: ");
            String numeroContrato = scanner.nextLine();
            System.out.print("O plano está ativo? (true/false): ");
            boolean ativo = scanner.nextBoolean();
            scanner.nextLine();

            PlanoDeSaude plano = new PlanoDeSaude(nomePlano, numeroContrato, ativo);
            petshopService.registrarPlanoDeSaude(nomeAnimal, plano);
        } catch (AnimalNaoEncontradoException | InputMismatchException e) {
            System.err.println("Erro: " + e.getMessage());
            scanner.nextLine();
        }
    }

    private static void verSuporte() {
        System.out.println("\n--- SUPORTE ---");
        System.out.println("Para obter ajuda, entre em contato:");
        System.out.println("Email: suporte@pets.com");
        System.out.println("Telefone: (11) 99999-9999");
    }

    private static void salvarDados() {
        try {
            petshopService.salvarDados();
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}