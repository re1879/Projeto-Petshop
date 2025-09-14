import java.io.IOException;
import java.util.List;

public interface GerenciadorPetshop {
    void cadastrarAnimal(Animal animal);
    void removerAnimal(String nomeAnimal) throws AnimalNaoEncontradoException;
    Animal pesquisarAnimal(String nomeAnimal) throws AnimalNaoEncontradoException;
    List<Animal> listarAnimais();
    void salvarDados() throws IOException;
    void carregarDados() throws IOException, ClassNotFoundException;
}