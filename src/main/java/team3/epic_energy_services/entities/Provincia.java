package team3.epic_energy_services.entities;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Provincia {
    private String nome;
    private String sigla;


    public Provincia(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }


}

