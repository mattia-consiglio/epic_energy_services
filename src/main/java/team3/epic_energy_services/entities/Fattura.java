package team3.epic_energy_services.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "Fattura")

public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private LocalDate data_emissione;
    private double importo;
    private String numero;

    @ManyToOne
    @JoinColumn(name = "stato_id")
    private StatoFattura stato;
}
