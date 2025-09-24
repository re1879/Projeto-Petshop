import java.io.Serializable;

public class PlanoDeSaude implements Serializable {
    private String nomePlano;
    private String numeroContrato;
    private boolean ativo;

    public PlanoDeSaude(String nomePlano, String numeroContrato, boolean ativo) {
        this.nomePlano = nomePlano;
        this.numeroContrato = numeroContrato;
        this.ativo = ativo;
    }

    @Override
    public String toString() {
        return "Plano: " + nomePlano + ", Contrato: " + numeroContrato + ", Status: " + (ativo ? "Ativo" : "Inativo");
    }
}