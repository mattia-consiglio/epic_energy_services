package team3.epic_energy_services.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team3.epic_energy_services.entities.Fattura;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FatturaRepository extends JpaRepository<Fattura, UUID> {
    Optional<Object> findByNumero(String numero);

    List<Fattura> findByCliente_Id(UUID id);

    Page<Fattura> findAll(Specification<Fattura> spec, Pageable pageable);


}