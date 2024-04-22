package team3.epic_energy_services.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Indirizzi {
    private String via;
    private String civico;
    private String localita;
    private String cap;
    private Comune comune;


    public Indirizzi(String via, String civico, String localita, String cap, Comune comune) {
        this.via = via;
        this.civico = civico;
        this.localita = localita;
        this.cap = cap;
        this.comune = comune;
    }


}
