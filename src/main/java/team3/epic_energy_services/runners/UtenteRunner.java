package team3.epic_energy_services.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import team3.epic_energy_services.payloads.UtenteDTO;
import team3.epic_energy_services.services.RuoloService;
import team3.epic_energy_services.services.StatoFatturaService;
import team3.epic_energy_services.services.UtenteService;

@Component
public class UtenteRunner implements CommandLineRunner {
    @Autowired
    private RuoloService ruoloService;

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private StatoFatturaService statoFatturaService;

    @Override
    public void run(String... args) throws Exception {

        if (!ruoloService.existsByRuolo("ADMIN")) {

            ruoloService.addRuolo("ADMIN");
        }
        if (!ruoloService.existsByRuolo("USER")) {
            ruoloService.addRuolo("USER");
        }

        if (!utenteService.existsByEmail("admin@admin.admin")) {
            utenteService.createUtente(new UtenteDTO("admin", "admin", "admin@admin.admin", "f1ropT43$XbIPYGAJkEU", "admin"), "ADMIN");
        }

        if (!statoFatturaService.existsByStato("PAGATA")) {
            statoFatturaService.createStatoFattura("PAGATA");
        }
        if (!statoFatturaService.existsByStato("NON_PAGATA")) {
            statoFatturaService.createStatoFattura("NON_PAGATA");
        }
        if (!statoFatturaService.existsByStato("INSOLUTA")) {
            statoFatturaService.createStatoFattura("INSOLUTA");
        }
    }
}
