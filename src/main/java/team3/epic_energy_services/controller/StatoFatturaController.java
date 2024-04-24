package team3.epic_energy_services.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team3.epic_energy_services.entities.StatoFattura;
import team3.epic_energy_services.payloads.StatoFatturaDTO;
import team3.epic_energy_services.services.StatoFatturaService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/stato")
public class StatoFatturaController {

    @Autowired
    private StatoFatturaService statoFatturaService;

    @GetMapping("/{id}")
    public StatoFattura getStatoFattura(@PathVariable UUID id) {
        return statoFatturaService.getStatoFattura(id);
    }

    @GetMapping
    public List<StatoFattura> getAllStatoFatture() {
        return statoFatturaService.getAllStatoFatture();
    }

    @PostMapping
    public StatoFattura createStatoFattura(@Valid @RequestBody StatoFatturaDTO statoFatturaDTO) {
        return statoFatturaService.createStatoFattura(statoFatturaDTO.stato());
    }

    @PutMapping("/{id}")
    public StatoFattura updateStatoFattura(@PathVariable UUID id, @Valid @RequestBody StatoFatturaDTO statoFatturaDTO) {
        return statoFatturaService.updateStatoFattura(id, statoFatturaDTO.stato());
    }

    @DeleteMapping("/{id}")
    public void deleteStatoFattura(@PathVariable UUID id) {
        statoFatturaService.deleteStatoFattura(id);
    }
}
