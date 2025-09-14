import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class PetshopService implements GerenciadorPetshop {

    private List<Animal> animais;
    private static final String NOME_ARQUIVO = "animais.dat";

    public PetshopService() {
        this.animais = new ArrayList<>();
    }

    @Override
    public void cadastrarAnimal(Animal animal) {
        animais.add(animal);
        System.out.println("Animal " + animal.getNome() + " cadastrado com sucesso!");
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
}