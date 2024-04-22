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
    private String nome;
    private String sigla;


    public Provincia(String nome, String sigla) {
        this.nome = nome;
        this.sigla = sigla;
    }


}

