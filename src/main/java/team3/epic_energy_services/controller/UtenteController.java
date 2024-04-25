package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team3.epic_energy_services.entities.Utente;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.payloads.UtenteDTO;
import team3.epic_energy_services.services.UtenteService;

import java.util.UUID;

@RestController
@RequestMapping("api/utenti")
@PreAuthorize("hasAuthority('ADMIN')")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;


    @GetMapping
    public Page<Utente> getAllUtenti(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "id") String sort) {
        return utenteService.getUtenti(page, size, sort);
    }


    @PutMapping("/{id}")
    public Utente updateUtente(@PathVariable UUID id, @RequestBody @Validated UtenteDTO utenteDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return utenteService.updateUtente(id, utenteDTO);
    }

    @PutMapping("/me")
    public Utente updateUtente(@AuthenticationPrincipal Utente utente, @RequestBody @Validated UtenteDTO utenteDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return utenteService.updateUtente(utente.getId(), utenteDTO);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUtente(@PathVariable UUID id) {
        utenteService.deleteUtente(id);
    }
}
