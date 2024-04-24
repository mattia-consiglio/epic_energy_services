package team3.epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Cliente;
import team3.epic_energy_services.entities.TipoRagioneSociale;
import team3.epic_energy_services.exceptions.ResourceNotFoundException;
import team3.epic_energy_services.payloads.ClienteDTO;
import team3.epic_energy_services.repositories.ClienteRepository;

import java.util.List;
import java.util.UUID;

@Service
public class ClienteService {

    @Autowired
    private ClienteRepository clienteRepository;

    
    public Cliente creaCliente(ClienteDTO clienteDTO) {
        Cliente cliente = new Cliente();
        cliente.setRagioneSociale(clienteDTO.ragioneSociale());
        cliente.setPartitaIva(clienteDTO.partitaIva());
        cliente.setEmail(clienteDTO.email());
        cliente.setPec(clienteDTO.pec());
        cliente.setTelefono(clienteDTO.telefono());
        cliente.setEmailContatto(clienteDTO.emailContatto());
        cliente.setNomeContatto(clienteDTO.nomeContatto());
        cliente.setTelefonoContatto(clienteDTO.telefonoContatto());

        cliente.setTipoRagioneSociale(TipoRagioneSociale.valueOf(clienteDTO.ragioneSociale()));


        return clienteRepository.save(cliente);
    }

    public List<Cliente> getClienti() {
        return clienteRepository.findAll();
    }

    public Cliente getClienteById(UUID id) {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente non trovato con id: " + id));
    }

    public Cliente aggiornaCliente(UUID id, ClienteDTO clienteDTO) {
        Cliente cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Cliente non trovato con id: " + id));
        cliente.setRagioneSociale(TipoRagioneSociale.valueOf(clienteDTO.ragioneSociale()));
        cliente.setPartitaIva(clienteDTO.partitaIva());
        cliente.setEmail(clienteDTO.email());
        cliente.setNomeContatto(clienteDTO.nomeContatto());


        return clienteRepository.save(cliente);
    }

    public void eliminaCliente(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente non trovato con id: " + id);
        }
        clienteRepository.deleteById(id);
    }
}

