import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Animal implements Serializable {
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private Dono dono;
    private Servico servicoContratado;
    private List<Internamento> historicoInternamentos;
    private PlanoDeSaude planoSaude;

    public Animal(String nome, String especie, String raca, int idade, Dono dono, Servico servicoContratado) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.dono = dono;
        this.servicoContratado = servicoContratado;
        this.historicoInternamentos = new ArrayList<>();
        this.planoSaude = null; // Inicialmente sem plano de saúde
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public String getRaca() {
        return raca;
    }

    public int getIdade() {
        return idade;
    }

    public Dono getDono() {
        return dono;
    }

    public Servico getServicoContratado() {
        return servicoContratado;
    }

    public List<Internamento> getHistoricoInternamentos() {
        return historicoInternamentos;
    }

    public PlanoDeSaude getPlanoSaude() {
        return planoSaude;
    }

    public void setPlanoSaude(PlanoDeSaude planoSaude) {
        this.planoSaude = planoSaude;
    }

    // Método para adicionar um novo internamento ao histórico
    public void adicionarInternamento(Internamento internamento) {
        this.historicoInternamentos.add(internamento);
    }

    // Sobrescrita dos métodos principais
    @Override
    public String toString() {
        return "Nome: " + nome + ", Espécie: " + especie + ", Raça: " + raca +
               ", Idade: " + idade + " anos, Dono: [" + dono.toString() +
               "], Serviço: [" + servicoContratado.toString() + "]";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Animal animal = (Animal) o;
        return Objects.equals(nome, animal.nome) &&
               Objects.equals(dono.getNome(), animal.dono.getNome());
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, dono.getNome());
    }
}