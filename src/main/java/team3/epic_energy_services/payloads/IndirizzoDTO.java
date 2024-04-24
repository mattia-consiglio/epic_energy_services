package team3.epic_energy_services.payloads;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.UUID;

public record IndirizzoDTO(
        @NotEmpty(message = "devi mettere una via")
        @Size(min = 2, max = 40, message = "la via deve contenere da un minimo di 2 caratteri and un massimo di 40")
        String via,

        @NotEmpty(message = "devi mettere un numero civico")
        @Size(min = 1, max = 400, message = "il numero civico deve contenere da un minimo di 1 caratteri and un massimo di 400")
        String civico,

        @NotEmpty(message = "devi mettere una località")
        @Size(min = 2, max = 40, message = "la località deve contenere da un minimo di 2 caratteri and un massimo di 40")
        String localita,

        @NotEmpty(message = "devi mettere un cap")
        @Size(min = 5, max = 5, message = "il cap deve contenere 5 caratteri")
        String cap,

        @NotNull(message = "devi mettere un comune tramite UUID")
        UUID comune
        ) {
}
