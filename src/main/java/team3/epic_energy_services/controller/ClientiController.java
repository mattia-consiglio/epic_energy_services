package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team3.epic_energy_services.entities.Cliente;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.payloads.ClienteDTO;
import team3.epic_energy_services.services.ClienteService;

import java.util.UUID;

@RestController
@RequestMapping("api/clienti")

public class ClientiController {

    @Autowired
    private ClienteService clienteService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente creaCliente(@RequestBody @Validated ClienteDTO clienteDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return clienteService.creaCliente(clienteDTO);
    }

    @GetMapping
    public Page<Cliente> getClienti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "dataUltimoContatto") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return clienteService.getClienti(pageable);
    }

    @GetMapping("/{id}")
    public Cliente getClienteById(@PathVariable UUID id) {
        return clienteService.getClienteById(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Cliente aggiornaCliente(@PathVariable UUID id, @RequestBody @Validated ClienteDTO clienteDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return clienteService.aggiornaCliente(id, clienteDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void eliminaCliente(@PathVariable UUID id) {
        clienteService.eliminaCliente(id);
    }


}
