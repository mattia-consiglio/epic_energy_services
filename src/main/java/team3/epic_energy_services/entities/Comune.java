
package team3.epic_energy_services.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Comune {
    private String nome;
    private Province provincia;

    public Comune(String nome, Province provincia) {
        this.nome = nome;
        this.provincia = provincia;
    }


}
