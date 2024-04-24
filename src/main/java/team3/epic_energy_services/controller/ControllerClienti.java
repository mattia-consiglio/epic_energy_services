package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team3.epic_energy_services.entities.Cliente;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.payloads.ClienteDTO;
import team3.epic_energy_services.services.ClienteService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/clienti")
public class ControllerClienti {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente creaCliente(@RequestBody @Validated ClienteDTO clienteDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return clienteService.creaCliente(clienteDTO);
    }

    @GetMapping
    public List<Cliente> getClienti() {
        return clienteService.getClienti();
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable UUID id) {
        return clienteService.getClienteById(id);
    }

    @PutMapping("/{id}")
    public Cliente aggiornaCliente(@PathVariable UUID id, @RequestBody @Validated ClienteDTO clienteDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return clienteService.aggiornaCliente(id, clienteDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void eliminaCliente(@PathVariable UUID id) {
        clienteService.eliminaCliente(id);
    }


}
