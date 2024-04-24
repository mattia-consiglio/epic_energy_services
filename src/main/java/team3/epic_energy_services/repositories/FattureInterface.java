package team3.epic_energy_services.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import team3.epic_energy_services.entities.Fattura;
import team3.epic_energy_services.entities.StatoFattura;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FattureInterface extends JpaRepository<Fattura, UUID> {
    Optional<Object> findByNumero(String numero);

    List<Fattura> findByCliente_Id(UUID id);

    List<Fattura> findAll(Specification<Fattura> spec);
}
