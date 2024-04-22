package team3.epic_energy_services.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UtenteDTO(
        @NotBlank(message = "Nome is required")
        @Size(min = 3, message = "Name must at least 3 characters long")
        String nome,
        @NotBlank(message = "Cognome is required")
        @Size(min = 3, message = "Surname must at least 3 characters long")
        String cognome,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        @NotBlank(message = "Password is required")
        @Size(min = 15, message = "Password must at least 15 characters long")
        String password,
        @NotBlank(message = "Username is required")
        String username
) {
}
