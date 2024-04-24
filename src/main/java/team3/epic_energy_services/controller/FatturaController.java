package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team3.epic_energy_services.entities.Fattura;
import team3.epic_energy_services.entities.StatoFattura;
import team3.epic_energy_services.payloads.FatturaDTO;
import team3.epic_energy_services.services.FatturaService;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/fattura")
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
    public Fattura getAllFatture() {
        return (Fattura) fatturaService.getAllFatture();
    }

    @PostMapping
    public Fattura createFattura(@RequestBody FatturaDTO fattura) {
        return fatturaService.createFattura(fattura);
    }

    @PutMapping
    public Fattura updateFattura(@RequestBody FatturaDTO fattura) {
        return fatturaService.updateFattura(fattura);
    }

    @DeleteMapping("/{id}")
    public void deleteFattura(@PathVariable UUID id) {
        fatturaService.deleteFattura(id);
    }

    @GetMapping("/filter")
    public List<Fattura> getFatturaByClienteStatoDataRangeImporto(
            @RequestParam(required = false) UUID clienteId,
            @RequestParam(required = false) StatoFattura stato,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(required = false) BigDecimal minImporto,
            @RequestParam(required = false) BigDecimal maxImporto)
           {
        return fatturaService.getFatturaByClienteStatoDataRangeImporto(clienteId, stato, startDate, endDate, minImporto, maxImporto);
    }

    @GetMapping("/year/{year}")
    public List<Fattura> getFatturaByYear(@PathVariable Integer year) {
        return fatturaService.getFatturaByYear(year);
    }
}
