package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import team3.epic_energy_services.entities.StatoFattura;
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
    public StatoFattura createStatoFattura(@RequestBody String statoFattura) {
        return statoFatturaService.createStatoFattura(statoFattura);
    }

    @PutMapping("/{id}")
    public StatoFattura updateStatoFattura(@PathVariable UUID id, @RequestBody String statoFattura) {
        return statoFatturaService.updateStatoFattura(id, statoFattura);
    }

    @DeleteMapping("/{id}")
    public void deleteStatoFattura(@PathVariable UUID id) {
        statoFatturaService.deleteStatoFattura(id);
    }
}
