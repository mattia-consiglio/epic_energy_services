
package team3.epic_energy_services.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Comune {
    private String nome;
    private Provincia provincia;

    public Comune(String nome, Provincia provincia) {
        this.nome = nome;
        this.provincia = provincia;
    }


}
