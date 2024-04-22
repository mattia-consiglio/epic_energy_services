package team3.epic_energy_services.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "Stato_fattura")
public class StatoFattura {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String stato;
}
