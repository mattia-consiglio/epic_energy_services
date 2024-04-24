package team3.epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.StatoFattura;
import team3.epic_energy_services.repositories.StatoFatturaRepository;

import java.util.List;
import java.util.UUID;

@Service
public class StatoFatturaService {

    @Autowired
    private StatoFatturaRepository statoFatturaRepository;

    public boolean existsByStato(String stato) {
        return statoFatturaRepository.existsByStato(stato);
    }

    public StatoFattura createStatoFattura(String statoFattura) {
        StatoFattura newStatoFattura = new StatoFattura();
        newStatoFattura.setStato(statoFattura);
        return statoFatturaRepository.save(newStatoFattura);
    }

    public StatoFattura getStatoFattura(UUID id) {
        return statoFatturaRepository.findById(id).orElse(null);
    }

    public void deleteStatoFattura(UUID id) {
        statoFatturaRepository.deleteById(id);
    }

    public List<StatoFattura> getAllStatoFatture() {
        return statoFatturaRepository.findAll();
    }

    public StatoFattura updateStatoFattura(UUID id, String statoFattura) {
        StatoFattura existingStatoFattura = statoFatturaRepository.findById(id).orElse(null);
        if (existingStatoFattura == null) {
            return null;
        }
        existingStatoFattura.setStato(statoFattura);
        return statoFatturaRepository.save(existingStatoFattura);
    }

}