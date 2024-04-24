package team3.epic_energy_services.payloads;

import jakarta.validation.constraints.NotBlank;

public record StatoFatturaDTO(@NotBlank(message = "Stato is required") String stato) {
}