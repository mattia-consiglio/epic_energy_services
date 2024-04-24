package team3.epic_energy_services.services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Indirizzo;
import team3.epic_energy_services.exceptions.RecordNotFoundException;
import team3.epic_energy_services.payloads.IndirizzoDTO;
import team3.epic_energy_services.repositories.IndirizzoRepository;

import java.util.UUID;

@Service
public class IndirizzoService {

    @Autowired
    private IndirizzoRepository indirizzoRepository;


    @Autowired
    private ComuneService comuneService;

    public Page<Indirizzo> getIndirizzi(int page, int size, String sortBy) {
        if (size > 50) size = 50;
        Pageable p = PageRequest.of(page, size, Sort.by(sortBy));
        return indirizzoRepository.findAll(p);
    }

    public Indirizzo add(IndirizzoDTO newIndirizzo) {
        return indirizzoRepository.save(new Indirizzo(
                newIndirizzo.via(),
                newIndirizzo.civico(),
                newIndirizzo.localita(),
                newIndirizzo.cap(),
                comuneService.getComuneById(newIndirizzo.comune())
        ));
    }

    public Indirizzo findById(UUID id) {
        return this.indirizzoRepository.findById(id).orElseThrow(() -> new RecordNotFoundException(id));
    }

    public void findByIdAndDelete(UUID id) {
        Indirizzo found = this.findById(id);
        this.indirizzoRepository.delete(found);
    }

    public Indirizzo findByIdAndUpdate(UUID id, IndirizzoDTO newIndirizzo) {
        Indirizzo found = this.findById(id);
        found.setVia(newIndirizzo.via());
        found.setCivico(newIndirizzo.civico());
        found.setLocalita(newIndirizzo.localita());
        found.setCap(newIndirizzo.cap());
        indirizzoRepository.save(found);
        return found;
    }

}
