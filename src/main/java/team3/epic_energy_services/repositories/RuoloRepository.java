package team3.epic_energy_services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team3.epic_energy_services.entities.Ruolo;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RuoloRepository extends JpaRepository<Ruolo, UUID> {
    public Optional<Ruolo> findByRuolo(String ruolo);

    boolean existsByRuolo(String ruolo);
}
