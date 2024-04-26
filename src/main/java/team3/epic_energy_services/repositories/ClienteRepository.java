package team3.epic_energy_services.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team3.epic_energy_services.entities.Cliente;
import team3.epic_energy_services.entities.Fattura;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByPartitaIva(int partitaIva);

    boolean existsByPartitaIva(int partitaIva);

    List<Cliente> findAll(Specification<Cliente> spec);
}
