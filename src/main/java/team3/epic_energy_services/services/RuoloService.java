package team3.epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Ruolo;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.repositories.RuoloRepository;

@Service
public class RuoloService {
    @Autowired
    private RuoloRepository ruoloRepository;


    public Ruolo getRuolo(String ruolo) {
        return ruoloRepository.findByRuolo(ruolo).orElseThrow(() -> new BadRequestException("Ruolo not found"));
    }

    public Ruolo addRuolo(String ruoloString) {
        Ruolo ruolo = new Ruolo(ruoloString);
        return ruoloRepository.save(ruolo);
    }

    public boolean existsByRuolo(String ruolo) {
        return ruoloRepository.existsByRuolo(ruolo);
    }
}
