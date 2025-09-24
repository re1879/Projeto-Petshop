import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class PetshopService implements GerenciadorPetshop {

    private List<Animal> animais;
    private static final String NOME_ARQUIVO = "animais.dat";

    public PetshopService() {
        this.animais = new ArrayList<>();
    }

    @Override
    public void cadastrarAnimal(Animal novoAnimal) throws AnimalJaCadastradoException {
        // Verifica se a lista já contém um animal que seja "igual" ao novo.
        if (animais.contains(novoAnimal)) {
            throw new AnimalJaCadastradoException("Erro: Animal com o mesmo nome e dono já cadastrado.");
        }
        
        animais.add(novoAnimal);
        System.out.println("Animal " + novoAnimal.getNome() + " cadastrado com sucesso!");
    }

    @Override
    public void removerAnimal(String nomeAnimal) throws AnimalNaoEncontradoException {
        boolean removido = animais.removeIf(a -> a.getNome().equalsIgnoreCase(nomeAnimal));
        if (!removido) {
            throw new AnimalNaoEncontradoException("O animal " + nomeAnimal + " não foi encontrado para remoção.");
        }
        System.out.println("Animal " + nomeAnimal + " removido com sucesso!");
    }

    @Override
    public Animal pesquisarAnimal(String nomeAnimal) throws AnimalNaoEncontradoException {
        for (Animal animal : animais) {
            if (animal.getNome().equalsIgnoreCase(nomeAnimal)) {
                return animal;
            }
        }
        throw new AnimalNaoEncontradoException("O animal " + nomeAnimal + " não foi encontrado na pesquisa.");
    }

    @Override
    public List<Animal> listarAnimais() {
        return new ArrayList<>(animais);
    }

    @Override
    public void salvarDados() throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(NOME_ARQUIVO))) {
            oos.writeObject(animais);
            System.out.println("Dados salvos com sucesso!");
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public void carregarDados() throws IOException, ClassNotFoundException {
        File arquivo = new File(NOME_ARQUIVO);
        if (arquivo.exists()) {
            try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(arquivo))) {
                animais = (List<Animal>) ois.readObject();
                System.out.println("Dados carregados com sucesso!");
            }
        } else {
            System.out.println("Arquivo de dados não encontrado. Iniciando com lista vazia.");
        }
    }

    @Override
    public void registrarInternamento(String nomeAnimal, Internamento internamento) throws AnimalNaoEncontradoException {
        Animal animal = pesquisarAnimal(nomeAnimal);
        animal.adicionarInternamento(internamento);
        System.out.println("Internamento registrado para o animal " + nomeAnimal);
    }

    @Override
    public void registrarPlanoDeSaude(String nomeAnimal, PlanoDeSaude plano) throws AnimalNaoEncontradoException {
        Animal animal = pesquisarAnimal(nomeAnimal);
        animal.setPlanoSaude(plano);
        System.out.println("Plano de saúde registrado para o animal " + nomeAnimal);
    }

    @Override
    public String getHistoricoInternamentos(String nomeAnimal) throws AnimalNaoEncontradoException {
        Animal animal = pesquisarAnimal(nomeAnimal);
        if (animal.getHistoricoInternamentos().isEmpty()) {
            return "Nenhum histórico de internamento para " + nomeAnimal;
        }
        return animal.getHistoricoInternamentos().stream()
                .map(Object::toString)
                .collect(Collectors.joining("\n"));
    }
}