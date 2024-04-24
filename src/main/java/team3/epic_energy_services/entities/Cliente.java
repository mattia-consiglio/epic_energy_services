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
@Table(name = "clienti")
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(value = AccessLevel.NONE)
    private UUID id;

    private String ragioneSociale;
    private int partitaIva;
    private String email;
    private LocalDate dataInserimento = LocalDate.now();
    private LocalDate dataUltimoContatto = LocalDate.now();
    private double fatturatoAnnuale = 0;
    private String pec;
    private String telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;
    @Enumerated(EnumType.STRING)
    private TipoRagioneSociale tipoRagioneSociale;

    @ManyToOne
    @JoinColumn(name = "sede_legale_id")
    private Indirizzo sedeLegale;

    @ManyToOne
    @JoinColumn(name = "sede_operativa_id")
    private Indirizzo sedeOperativa;


}

