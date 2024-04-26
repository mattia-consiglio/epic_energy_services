package team3.epic_energy_services.services;

import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Predicate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Cliente;
import team3.epic_energy_services.entities.Fattura;
import team3.epic_energy_services.entities.StatoFattura;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.payloads.FatturaDTO;
import team3.epic_energy_services.payloads.StatoFatturaDTO;
import team3.epic_energy_services.repositories.FattureInterface;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;


@Service
public class FatturaService {
    private final FattureInterface fattureInterface;
    private final StatoFatturaService statoFatturaService;
    private final ClienteService clienteService;


    @Autowired
    public FatturaService(FattureInterface fattureInterface, StatoFatturaService statoFatturaService, ClienteService clienteService) {
        this.fattureInterface = fattureInterface;
        this.statoFatturaService = statoFatturaService;
        this.clienteService = clienteService;
    }

    public Fattura getFattura(UUID id) {
        return fattureInterface.findById(id).orElseThrow(() -> new BadRequestException("Fattura not found"));
    }

    public Fattura getFatturaByNumero(String numero) {
        return (Fattura) fattureInterface.findByNumero(numero).orElseThrow(() -> new BadRequestException("Fattura not found"));
    }

    public Page<Fattura> getAllFatture(Pageable pageable) {
        return fattureInterface.findAll(pageable);
    }

    public Fattura createFattura(FatturaDTO fattura) {
        Fattura newFattura = new Fattura();
        newFattura.setNumero(fattura.numero());
        newFattura.setDataEmissione(fattura.data_emissione());
        newFattura.setImporto(fattura.importo());
        StatoFattura statoFattura = statoFatturaService.getStatoFatturaByStato("NON_PAGATA");
        newFattura.setStato(statoFattura);
        Cliente cliente = clienteService.getClienteById(fattura.clienteId());
        cliente = clienteService.updateDataUltimoContatto(cliente);
        cliente = clienteService.updateFatturatoAnnuale(cliente, newFattura.getImporto());
        newFattura.setCliente(cliente);
        return fattureInterface.save(newFattura);
    }


    public Fattura updateStatoFattura(UUID id, StatoFatturaDTO statoFatturaDTO) {
        Fattura existingFattura = this.getFattura(id);
        StatoFattura statoFattura = statoFatturaService.getStatoFatturaByStato(statoFatturaDTO.stato());
        existingFattura.setStato(statoFattura);
        return fattureInterface.save(existingFattura);
    }

    public void deleteFattura(UUID id) {
        Fattura existingFattura = getFattura(id);
        fattureInterface.delete(existingFattura);
    }

    public Specification<Fattura> getFatturaSpecification(UUID clienteId, StatoFattura stato, LocalDate startDate, LocalDate endDate, Double minImporto, Double maxImporto, Integer year) {

        return (root, query, cb) -> {
            Predicate p = cb.conjunction();
            if (clienteId != null) {
                p = cb.and(p, cb.equal(root.get("cliente").get("id"), clienteId));
            }
            if (stato != null) {
                p = cb.and(p, cb.equal(root.get("stato"), stato));
            }
            if (startDate != null && endDate != null) {
                p = cb.and(p, cb.between(root.get("dataEmissione"), startDate, endDate));
            }
            if (minImporto != null && maxImporto != null) {
                p = cb.and(p, cb.between(root.get("importo"), minImporto, maxImporto));
            }
            if (year != null) {
                LocalDate startOfYear = LocalDate.of(year, 1, 1);
                LocalDate endOfYear = LocalDate.of(year, 12, 31);
                p = cb.and(p, cb.between(root.get("dataEmissione"), startOfYear, endOfYear));
            }
            return p;
        };
    }

    public List<Fattura> getFatturaByClienteStatoDataRangeImporto(UUID clienteId, StatoFattura stato, LocalDate startDate, LocalDate endDate, Double minImporto, Double maxImporto, Integer year) {
        Specification<Fattura> spec = getFatturaSpecification(clienteId, stato, startDate, endDate, minImporto, maxImporto, year);
        return fattureInterface.findAll(spec);
    }

}