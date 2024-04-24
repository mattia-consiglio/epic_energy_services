package team3.epic_energy_services.payloads;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record ClienteDTO(
        @NotBlank(message = "Nome is required")
        @Size(min = 3, message = "Name must at least 3 characters long")
        String ragioneSociale,
        @NotNull(message = "Partita iva is required")
        int partitaIva,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @NotBlank(message = "Email is required")
        @Email
        String pec,
        @NotBlank(message = "Telefono is required")
        String telefono,
        @NotBlank(message = "Email di contatto is required")
        @Email
        String emailContatto,
        @NotBlank(message = "Nome is required")
        @Size(min = 3, message = "Nome must at least 3 characters long")
        String nomeContatto,
        @NotBlank(message = "Cognome is required")
        @Size(min = 3, message = "Cognome must at least 3 characters long")
        String cognomeContatto,
        @NotBlank(message = "Telefono contatto is required")
        @Size(min = 10, max = 20, message = "Telefono contatto must between 10 and 20 characters long")
        String telefonoContatto,
        @NotNull(message = "ID Sede legale is required")
        UUID sedeLegaleId,
        @NotNull(message = "ID Sede operativa is required")
        UUID sedeOperativaID,

        @NotBlank(message = "Ragione sociale is required")
        @Pattern(regexp = "PA|SAS|SPA|SRL", message = "Ragione sociale must be one of the following: PA, SAS, SPA, SRL")
        String tipoRagioneSociale
) {
}
