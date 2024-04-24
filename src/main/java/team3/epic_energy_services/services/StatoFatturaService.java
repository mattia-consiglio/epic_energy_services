package team3.epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.StatoFattura;
import team3.epic_energy_services.repositories.StatoFatturaRepository;

@Service
public class StatoFatturaService {

    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    public boolean existsByStato(String stato) {
        return statoFatturaRepository.existsByStato(stato);
    }

    public StatoFattura createStatoFattura(String stato) {
        StatoFattura statoFattura = new StatoFattura();
        statoFattura.setStato(stato);
        return statoFatturaRepository.save(statoFattura);
    }
}