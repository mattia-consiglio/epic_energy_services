package team3.epic_energy_services.services;

import jakarta.persistence.criteria.Predicate;
import kong.unirest.HttpResponse;
import kong.unirest.JsonNode;
import kong.unirest.Unirest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
import java.time.format.DateTimeFormatter;
import java.util.UUID;


@Service
public class FatturaService {
    @Autowired
    private FattureInterface fattureInterface;
    @Autowired
    private StatoFatturaService statoFatturaService;
    @Autowired
    private ClienteService clienteService;


    private String mailgunApiKey;

    private String mailgunDomain;

    public FatturaService(@Value("${mailgun.apikey}") String mailgunApiKey, @Value("${mailgun.domain}") String mailgunDomain) {
        this.mailgunApiKey = mailgunApiKey;
        this.mailgunDomain = mailgunDomain;
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
        this.sendEmail(newFattura);
        return fattureInterface.save(newFattura);
    }

    public void sendEmail(Fattura fattura) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        HttpResponse<JsonNode> response = Unirest.post("https://api.mailgun.net/v3/" + this.mailgunDomain + "/messages")
                .basicAuth("api", this.mailgunApiKey)
                .queryString("from", "info@" + this.mailgunDomain)
                .queryString("to", fattura.getCliente().getEmail())
                .queryString("subject", "Emessa fattura numero " + fattura.getNumero())
                .queryString("text", "Spett.le " + fattura.getCliente().getRagioneSociale() + ",\n" + "La fattura numero " + fattura.getNumero() + " è stata emessa in data " + fattura.getDataEmissione().format(formatter) + ".\nCordiali saluti Epic Energy Services.")
                .queryString("html", "<html><body><p>Spett.le " + fattura.getCliente().getRagioneSociale() + ",</p><p>La fattura numero <b>" + fattura.getNumero() + "</b> è stata emessa in data " + fattura.getDataEmissione().format(formatter) + ".</p><p>Cordiali saluti Epic Energy Services.</p></body></html>")
                .asJson();
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

    public Page<Fattura> getFatturaByClienteStatoDataRangeImporto(UUID clienteId, StatoFattura stato, LocalDate startDate, LocalDate endDate, Double minImporto, Double maxImporto, Integer year, Pageable pageable) {
        Specification<Fattura> spec = getFatturaSpecification(clienteId, stato, startDate, endDate, minImporto, maxImporto, year);
        return fattureInterface.findAll(spec, pageable);
    }

}