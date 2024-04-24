package team3.epic_energy_services.payloads;

import jakarta.validation.constraints.*;

import java.util.UUID;

public record ClienteDTO(
        @NotBlank(message = "Ragione sociale is required")
        @Size(min = 3, message = "Ragione sociale at least 3 characters long")
        String ragioneSociale,
        @NotNull(message = "Partita iva is required")
        int partitaIva,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @NotBlank(message = "Pec is required")
        @Email
        String pec,
        @NotBlank(message = "Telefono is required")
        String telefono,
        @NotBlank(message = "Email di contatto is required")
        @Email
        String emailContatto,
        @NotBlank(message = "Nome di contatto is required")
        @Size(min = 3, message = "Nome di contatto at least 3 characters long")
        String nomeContatto,
        @NotBlank(message = "Cognome di contatto is required")
        @Size(min = 3, message = "Cognome di contatto must at least 3 characters long")
        String cognomeContatto,
        @NotBlank(message = "Telefono di contatto is required")
        @Size(min = 10, max = 20, message = "Telefono di contatto must between 10 and 20 characters long")
        String telefonoContatto,
        @NotNull(message = "ID Sede legale is required")
        UUID sedeLegaleId,
        @NotNull(message = "ID Sede operativa is required")
        UUID sedeOperativaId,

        @NotBlank(message = "Tipo ragione sociale is required")
        @Pattern(regexp = "PA|SAS|SPA|SRL", message = "Ragione sociale must be one of the following: PA, SAS, SPA, SRL")
        String tipoRagioneSociale
) {
}
