package team3.epic_energy_services.repositories;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import team3.epic_energy_services.entities.Fattura;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FattureInterface extends JpaRepository<Fattura, UUID> {
    Optional<Object> findByNumero(String numero);

    List<Fattura> findByCliente_Id(UUID id);

    List<Fattura> findAll(Specification<Fattura> spec);

    @Query(value = "SELECT * FROM Fatture f WHERE EXTRACT(YEAR FROM f.dataEmissione) = :year", nativeQuery = true)
    List<Fattura> findFatturaByYear(@Param("year") Integer year);
}
