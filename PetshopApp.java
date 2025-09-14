import java.io.IOException;
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

        int opcao;
        do {
            exibirMenu();
            opcao = scanner.nextInt();
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
                    salvarDados();
                    break;
                case 6:
                    System.out.println("Saindo do sistema. Até mais!");
                    break;
                default:
                    System.out.println("Opção inválida. Tente novamente.");
            }
        } while (opcao != 6);
    }

    private static void exibirMenu() {
        System.out.println("\n--- MENU PETSHOP ---");
        System.out.println("1. Cadastrar novo animal");
        System.out.println("2. Remover animal");
        System.out.println("3. Pesquisar animal");
        System.out.println("4. Listar todos os animais");
        System.out.println("5. Salvar dados");
        System.out.println("6. Sair");
        System.out.print("Escolha uma opção: ");
    }

    private static void cadastrarAnimal() {
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
        Servico servico = new Servico(descricaoServico, valorServico);

        Animal animal = new Animal(nome, especie, raca, idade, dono, servico);
        petshopService.cadastrarAnimal(animal);
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
            petshopService.listarAnimais().forEach(System.out::println);
        }
    }

    private static void salvarDados() {
        try {
            petshopService.salvarDados();
        } catch (IOException e) {
            System.err.println("Erro ao salvar os dados: " + e.getMessage());
        }
    }
}