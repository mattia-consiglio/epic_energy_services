package team3.epic_energy_services.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "fatture")
public class Fattura {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(value = AccessLevel.NONE)
    private UUID id;
    private LocalDate data_emissione;
    private double importo;
    private String numero;

    @ManyToOne
    @JoinColumn(name = "stato_id")
    private StatoFattura stato;
    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    public void setDataEmissione(LocalDate localDate) {
        this.data_emissione = localDate;
    }
}
