package team3.epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Cliente;
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
        Cliente nuovoCliente = new Cliente();
        nuovoCliente.setRagioneSociale(clienteDTO.getRagioneSocialeEnum());
        nuovoCliente.setPartitaIva(clienteDTO.getPartitaIva());
        nuovoCliente.setEmail(clienteDTO.getEmail());


        return clienteRepository.save(nuovoCliente);
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
        cliente.setRagioneSociale(clienteDTO.getRagioneSocialeEnum());
        cliente.setPartitaIva(clienteDTO.getPartitaIva());
        cliente.setEmail(clienteDTO.getEmail());


        return clienteRepository.save(cliente);
    }

    public void eliminaCliente(UUID id) {
        if (!clienteRepository.existsById(id)) {
            throw new ResourceNotFoundException("Cliente non trovato con id: " + id);
        }
        clienteRepository.deleteById(id);
    }
}

