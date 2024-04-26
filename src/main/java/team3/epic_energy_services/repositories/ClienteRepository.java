package team3.epic_energy_services.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team3.epic_energy_services.entities.Cliente;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface ClienteRepository extends JpaRepository<Cliente, UUID> {
    Optional<Cliente> findByPartitaIva(int partitaIva);

    boolean existsByPartitaIva(int partitaIva);

    Page<Cliente> findAll(Specification<Cliente> spec, Pageable pageable);
}
