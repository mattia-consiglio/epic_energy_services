package team3.epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Cliente;
import team3.epic_energy_services.entities.Fattura;
import team3.epic_energy_services.entities.StatoFattura;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.payloads.FatturaDTO;
import team3.epic_energy_services.repositories.ClienteRepository;
import team3.epic_energy_services.repositories.FattureInterface;
import team3.epic_energy_services.repositories.StatoFatturaRepository;
import jakarta.persistence.criteria.Predicate;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;


@Service
public class FatturaService {
    private final FattureInterface fattureInterface;
    private final StatoFatturaRepository statoFatturaRepository;
    private final ClienteRepository clienteRepository;

    @Autowired
    public FatturaService(FattureInterface fattureInterface, StatoFatturaRepository statoFatturaRepository, ClienteRepository clienteRepository) {
        this.fattureInterface = fattureInterface;
        this.statoFatturaRepository = statoFatturaRepository;
        this.clienteRepository = clienteRepository;
    }

    public Fattura getFattura(UUID id) {
        return fattureInterface.findById(id).orElseThrow(() -> new BadRequestException("Fattura not found"));
    }

    public Fattura getFatturaByNumero(String numero) {
        return (Fattura) fattureInterface.findByNumero(numero).orElseThrow(() -> new BadRequestException("Fattura not found"));
    }

    public List<Fattura> getAllFatture() {
        return fattureInterface.findAll();
    }

    public Fattura createFattura(FatturaDTO fattura) {
        Fattura newFattura = new Fattura();
        newFattura.setNumero(fattura.numero());
        newFattura.setDataEmissione(fattura.data_emissione());
        newFattura.setImporto(fattura.importo());
        StatoFattura statoFattura = statoFatturaRepository.findById(fattura.statoId())
                .orElseThrow(() -> new BadRequestException("StatoFattura not found"));
        newFattura.setStato(statoFattura);
        Cliente cliente = clienteRepository.findById(fattura.clienteId())
                .orElseThrow(() -> new BadRequestException("Cliente not found"));
        newFattura.setCliente(cliente);
        return fattureInterface.save(newFattura);
    }

    public Fattura updateFattura(FatturaDTO fattura) {
        Fattura existingFattura = getFatturaByNumero(fattura.numero());
        existingFattura.setDataEmissione(fattura.data_emissione());
        existingFattura.setImporto(fattura.importo());
        StatoFattura statoFattura = statoFatturaRepository.findById(fattura.statoId())
                .orElseThrow(() -> new BadRequestException("StatoFattura not found"));
        existingFattura.setStato(statoFattura);
        Cliente cliente = clienteRepository.findById(fattura.clienteId())
                .orElseThrow(() -> new BadRequestException("Cliente not found"));
        existingFattura.setCliente(cliente);
        return fattureInterface.save(existingFattura);
    }

    public void deleteFattura(UUID id) {
        Fattura existingFattura = getFattura(id);
        fattureInterface.delete(existingFattura);
    }

    public List<Fattura> getFatturaByClienteStatoDataRangeImporto(String cliente, StatoFattura stato, LocalDate startDate, LocalDate endDate, BigDecimal minImporto, BigDecimal maxImporto) {
        return fattureInterface.findAll((root, query, cb) -> {
            Predicate p = cb.conjunction();
            if (cliente != null) {
                p = cb.and(p, cb.equal(root.get("cliente"), cliente));
            }
            if (stato != null) {
                p = cb.and(p, cb.equal(root.get("stato"), stato));
            }
            if (startDate != null && endDate != null) {
                p = cb.and(p, cb.between(root.get("data"), startDate, endDate));
            }
            if (minImporto != null && maxImporto != null) {
                p = cb.and(p, cb.between(root.get("importo"), minImporto, maxImporto));
            }
            return p;
        });
    }

}
