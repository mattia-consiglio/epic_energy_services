package team3.epic_energy_services.services;

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
import team3.epic_energy_services.repositories.FatturaRepository;

import java.time.LocalDate;
import java.util.UUID;


@Service
public class FatturaService {
    @Autowired
    private FatturaRepository fatturaRepository;

    @Autowired
    private StatoFatturaService statoFatturaService;

    @Autowired
    private ClienteService clienteService;

    public Fattura getFattura(UUID id) {
        return fatturaRepository.findById(id).orElseThrow(() -> new BadRequestException("Fattura not found"));
    }

    public Fattura getFatturaByNumero(String numero) {
        return (Fattura) fatturaRepository.findByNumero(numero).orElseThrow(() -> new BadRequestException("Fattura not found"));
    }

    public Page<Fattura> getAllFatture(Pageable pageable) {
        return fatturaRepository.findAll(pageable);
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
        return fatturaRepository.save(newFattura);
    }


    public Fattura updateStatoFattura(UUID id, StatoFatturaDTO statoFatturaDTO) {
        Fattura existingFattura = this.getFattura(id);
        StatoFattura statoFattura = statoFatturaService.getStatoFatturaByStato(statoFatturaDTO.stato());
        existingFattura.setStato(statoFattura);
        return fatturaRepository.save(existingFattura);
    }

    public void deleteFattura(UUID id) {
        Fattura existingFattura = getFattura(id);
        fatturaRepository.delete(existingFattura);
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

    public Page<Fattura> getFatturaByClienteStatoDataRangeImporto(UUID clienteId, StatoFattura stato, LocalDate startDate, LocalDate endDate, Double minImporto, Double maxImporto, Integer year, Pageable pageable) {
        Specification<Fattura> spec = getFatturaSpecification(clienteId, stato, startDate, endDate, minImporto, maxImporto, year);
        return fatturaRepository.findAll(spec, pageable);
    }

}