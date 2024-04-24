package team3.epic_energy_services.payloads;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import team3.epic_energy_services.entities.TypeCustomers;

public record ClienteDTO(
        @NotBlank(message = "Nome is required")
        @Size(min = 3, message = "Name must at least 3 characters long")
        String nome,
        @NotBlank(message = "Cognome is required")
        @Size(min = 3, message = "Surname must at least 3 characters long")
        String cognome,
        @NotBlank(message = "Email is required")
        @Email
        String email,
        int partitaIva,
        TypeCustomers ragioneSocialeEnum
) {
    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getEmail() {
        return email;
    }

    public int getPartitaIva() {
        return partitaIva;
    }

    public TypeCustomers getRagioneSocialeEnum() {
        return ragioneSocialeEnum;
    }
}
