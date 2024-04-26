package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import team3.epic_energy_services.entities.Fattura;
import team3.epic_energy_services.entities.StatoFattura;
import team3.epic_energy_services.payloads.FatturaDTO;
import team3.epic_energy_services.payloads.StatoFatturaDTO;
import team3.epic_energy_services.services.FatturaService;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/fatture")
public class FatturaController {

    @Autowired
    private FatturaService fatturaService;

    @GetMapping("/{id}")
    public Fattura getFattura(@PathVariable UUID id) {
        return fatturaService.getFattura(id);
    }

    @GetMapping("/numero/{numero}")
    public Fattura getFatturaByNumero(@PathVariable String numero) {
        return fatturaService.getFatturaByNumero(numero);
    }

    @GetMapping
    public Page<Fattura> getAllFatture(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "date") String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return fatturaService.getAllFatture(pageable);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura createFattura(@RequestBody FatturaDTO fattura) {
        return fatturaService.createFattura(fattura);
    }

    @PutMapping("{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Fattura updateFattura(@PathVariable UUID id, @RequestBody StatoFatturaDTO fattura) {
        return fatturaService.updateStatoFattura(id, fattura);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteFattura(@PathVariable UUID id) {
        fatturaService.deleteFattura(id);
    }

    @GetMapping("/filter")
    public List<Fattura> getFatturaByClienteStatoDataRangeImporto(
            @RequestParam(required = false) UUID clienteId,
            @RequestParam(required = false) StatoFattura stato,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) Double minImporto,
            @RequestParam(required = false) Double maxImporto) {
        return fatturaService.getFatturaByClienteStatoDataRangeImporto(clienteId, stato, startDate, endDate, minImporto, maxImporto);
    }

    @GetMapping("/year/{year}")
    public List<Fattura> getFatturaByYear(@PathVariable Integer year) {
        return fatturaService.getFatturaByYear(year);
    }
}
