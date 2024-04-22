package team3.epic_energy_services.entities;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Entity
@Getter
@Setter
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Setter(value = AccessLevel.NONE)
    private UUID id;
    private String username;
    private String password;
    private String email;
    private String firstName;
    private String lastName;
    @ManyToOne
    @JoinColumn(name = "role_id")
    private Ruolo role;
    private String avatarUrl;
}
