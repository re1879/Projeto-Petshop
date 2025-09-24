import java.io.Serializable;
import java.time.LocalDate;

public class Internamento implements Serializable {
    private String motivo;
    private LocalDate dataEntrada;
    private LocalDate dataSaida; // Pode ser null se o animal ainda estiver internado
    private String observacoes;

    public Internamento(String motivo, LocalDate dataEntrada, String observacoes) {
        this.motivo = motivo;
        this.dataEntrada = dataEntrada;
        this.observacoes = observacoes;
    }

    public void setDataSaida(LocalDate dataSaida) {
        this.dataSaida = dataSaida;
    }

    @Override
    public String toString() {
        return "Motivo: " + motivo + ", Entrada: " + dataEntrada + ", Saída: " + (dataSaida != null ? dataSaida : "Em andamento") + ", Obs: " + observacoes;
    }
}