package team3.epic_energy_services.payloads;

import jakarta.validation.constraints.NotBlank;
import java.time.LocalDate;
import java.util.UUID;

public record FatturaDTO(
        @NotBlank(message = "Numero is required")
        String numero,
        @NotBlank(message = "Data emissione is required")
        LocalDate data_emissione,
        @NotBlank(message = "Importo is required")
        double importo,
        @NotBlank(message = "Stato is required")
        UUID statoId,
        @NotBlank(message = "Cliente is required")
        UUID clienteId
) {
        public UUID statoId() {
                return statoId;
        }

        public UUID clienteId() {
                return clienteId;
        }
}