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

    @Enumerated(EnumType.STRING)
    private TypeCustomers ragioneSociale;

    private int partitaIva;
    private String email;
    private LocalDate dataInserimento;
    private LocalDate dataUltimoContatto;
    private double fatturatoAnnuale;
    private String pec;
    private int telefono;
    private String emailContatto;
    private String nomeContatto;
    private String cognomeContatto;
    private String telefonoContatto;
    private String logoAziendale;

    @ManyToOne
    @JoinColumn(name = "sede_legale_id")
    private Indirizzo sedeLegale;

    @ManyToOne
    @JoinColumn(name = "sede_operativa_id")
    private Indirizzo sedeOperativa;

    public Cliente(TypeCustomers ragioneSociale, int partitaIva, String email, LocalDate dataInserimento, LocalDate dataUltimoContatto, double fatturatoAnnuale, String pec, int telefono, String emailContatto, String nomeContatto, String cognomeContatto, String telefonoContatto, String logoAziendale, Indirizzo sedeLegale, Indirizzo sedeOperativa) {
        this.ragioneSociale = ragioneSociale;
        this.partitaIva = partitaIva;
        this.email = email;
        this.dataInserimento = dataInserimento;
        this.dataUltimoContatto = dataUltimoContatto;
        this.fatturatoAnnuale = fatturatoAnnuale;
        this.pec = pec;
        this.telefono = telefono;
        this.emailContatto = emailContatto;
        this.nomeContatto = nomeContatto;
        this.cognomeContatto = cognomeContatto;
        this.telefonoContatto = telefonoContatto;
        this.logoAziendale = logoAziendale;
        this.sedeLegale = sedeLegale;
        this.sedeOperativa = sedeOperativa;
    }
}

