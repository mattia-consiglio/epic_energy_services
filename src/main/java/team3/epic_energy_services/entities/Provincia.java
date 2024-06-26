package team3.epic_energy_services.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Setter
@Getter
@Entity
@NoArgsConstructor
@Table(name = "province")
public class Provincia {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(value = AccessLevel.NONE)
    private UUID id;
    @Column(unique = true)
    private String sigla;
    private String nome;
    private String regione;


    public Provincia(String sigla, String nome, String regione) {
        this.nome = nome;
        this.sigla = sigla;
        this.regione = regione;
    }


}

