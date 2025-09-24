import java.io.IOException;
import java.util.List;

public interface GerenciadorPetshop {
    void cadastrarAnimal(Animal animal) throws AnimalJaCadastradoException;
    void removerAnimal(String nomeAnimal) throws AnimalNaoEncontradoException;
    Animal pesquisarAnimal(String nomeAnimal) throws AnimalNaoEncontradoException;
    List<Animal> listarAnimais();
    void salvarDados() throws IOException;
    void carregarDados() throws IOException, ClassNotFoundException;

    void registrarInternamento(String nomeAnimal, Internamento internamento) throws AnimalNaoEncontradoException;
    void registrarPlanoDeSaude(String nomeAnimal, PlanoDeSaude plano) throws AnimalNaoEncontradoException;
    String getHistoricoInternamentos(String nomeAnimal) throws AnimalNaoEncontradoException;
}