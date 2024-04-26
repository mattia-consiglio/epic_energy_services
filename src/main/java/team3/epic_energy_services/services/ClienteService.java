package team3.epic_energy_services.services;

import jakarta.persistence.criteria.Predicate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Cliente;
import team3.epic_energy_services.entities.Indirizzo;
import team3.epic_energy_services.entities.TipoRagioneSociale;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.exceptions.ResourceNotFoundException;
import team3.epic_energy_services.payloads.ClienteDTO;
import team3.epic_energy_services.repositories.ClienteRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
@Slf4j
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private IndirizzoService indirizzoService;


    public Cliente creaCliente(ClienteDTO clienteDTO) {
        if (clienteRepository.existsByPartitaIva(clienteDTO.partitaIva())) {
            throw new BadRequestException("Cliente già presente");
        }
        Cliente cliente = new Cliente();
        Indirizzo sedeLegale = indirizzoService.findById(clienteDTO.sedeLegaleId());
        Indirizzo sedeOperativa = indirizzoService.findById(clienteDTO.sedeOperativaId());
        cliente.setRagioneSociale(clienteDTO.ragioneSociale());
        cliente.setPartitaIva(clienteDTO.partitaIva());
        cliente.setEmail(clienteDTO.email());
        cliente.setPec(clienteDTO.pec());
        cliente.setTelefono(clienteDTO.telefono());
        cliente.setEmailContatto(clienteDTO.emailContatto());
        cliente.setNomeContatto(clienteDTO.nomeContatto());
        cliente.setCognomeContatto(clienteDTO.cognomeContatto());
        cliente.setTelefonoContatto(clienteDTO.telefonoContatto());
        cliente.setSedeLegale(sedeLegale);
        cliente.setSedeOperativa(sedeOperativa);
        cliente.setTipoRagioneSociale(TipoRagioneSociale.valueOf(clienteDTO.tipoRagioneSociale()));
        String logoUrl = "https://ui-avatars.com/api/?name=" + clienteDTO.ragioneSociale().charAt(0);
        cliente.setLogoAziendale(logoUrl);

        return clienteRepository.save(cliente);
    }

    public Page<Cliente> getClienti(int page, int size, String sort) {
        if (Objects.equals(sort, "sedeLegale")) {
            sort = "sedeLegale.comune.provincia.nome";
        }
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return clienteRepository.findAll(pageable);
    }

    public Cliente getClienteById(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente non trovato con id: " + id));
    }

    public Cliente aggiornaCliente(UUID id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente non trovato con id: " + id));
        Indirizzo sedeLegale = indirizzoService.findById(clienteDTO.sedeLegaleId());
        Indirizzo sedeOperativa = indirizzoService.findById(clienteDTO.sedeOperativaId());
        cliente.setRagioneSociale(clienteDTO.ragioneSociale());
        cliente.setPartitaIva(clienteDTO.partitaIva());
        cliente.setEmail(clienteDTO.email());
        cliente.setPec(clienteDTO.pec());
        cliente.setTelefono(clienteDTO.telefono());
        cliente.setEmailContatto(clienteDTO.emailContatto());
        cliente.setNomeContatto(clienteDTO.nomeContatto());
        cliente.setCognomeContatto(clienteDTO.cognomeContatto());
        cliente.setTelefonoContatto(clienteDTO.telefonoContatto());
        cliente.setSedeLegale(sedeLegale);
        cliente.setSedeOperativa(sedeOperativa);
        cliente.setTipoRagioneSociale(TipoRagioneSociale.valueOf(clienteDTO.tipoRagioneSociale()));
        String logoUrl = "https://ui-avatars.com/api/?name=" + clienteDTO.ragioneSociale().charAt(0);
        cliente.setLogoAziendale(logoUrl);

        return clienteRepository.save(cliente);
    }

    public void eliminaCliente(UUID id) {

        clienteRepository.delete(this.getClienteById(id));
    }

    public Cliente updateDataUltimoContatto(Cliente cliente) {
        cliente.setDataUltimoContatto(LocalDate.now());
        return clienteRepository.save(cliente);
    }

    public Cliente updateFatturatoAnnuale(Cliente cliente, double fatturato) {
        cliente.setFatturatoAnnuale(cliente.getFatturatoAnnuale() + fatturato);
        return clienteRepository.save(cliente);
    }

    public Specification<Cliente> getClienteSpecification(Double fatturatoAnnuale, LocalDate dataInserimento, LocalDate dataUltimoContatto, String ragioneSociale) {

        return (root, query, cb) -> {
            Predicate p = cb.conjunction();
            if (fatturatoAnnuale != null) {
                p = cb.and(p, cb.equal(root.get("fatturatoAnnuale"), fatturatoAnnuale));
            }
            if (dataInserimento != null) {
                p = cb.and(p, cb.equal(root.get("dataInserimento"), dataInserimento));
            }
            if (dataUltimoContatto != null) {
                p = cb.and(p, cb.equal(root.get("dataUltimoContatto"), dataUltimoContatto));
            }
            if (ragioneSociale != null) {
                p = cb.and(p, cb.like(root.get("ragioneSociale"), "%" + ragioneSociale + "%"));
            }
            return p;
        };
    }

    public List<Cliente> getClientiByTipoRagioneSociale(Double fatturatoAnnuale, LocalDate dataInserimento, LocalDate dataUltimoContatto, String ragioneSociale) {
        Specification<Cliente> spec = getClienteSpecification(fatturatoAnnuale, dataInserimento, dataUltimoContatto, ragioneSociale);
        return clienteRepository.findAll(spec);
    }
}

