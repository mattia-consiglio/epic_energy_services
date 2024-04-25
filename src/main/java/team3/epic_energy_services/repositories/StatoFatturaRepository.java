package team3.epic_energy_services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import team3.epic_energy_services.entities.StatoFattura;

import java.util.Optional;
import java.util.UUID;


public interface StatoFatturaRepository extends JpaRepository<StatoFattura, UUID> {
    boolean existsByStato(String stato);

    Optional<StatoFattura> findByStato(String stato);
}
