package team3.epic_energy_services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team3.epic_energy_services.entities.Provincia;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProvinciaRepository extends JpaRepository<Provincia, UUID> {
    boolean existsByNomeIgnoreCase(String nome);

    Optional<Provincia> findByNomeIgnoreCase(String nome);

    long count();
}
