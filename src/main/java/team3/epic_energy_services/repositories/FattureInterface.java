package team3.epic_energy_services.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team3.epic_energy_services.entities.Fattura;

import java.util.UUID;

@Repository
public interface FattureInterface extends JpaRepository<Fattura, UUID> {
}
