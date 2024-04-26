package team3.epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Comune;
import team3.epic_energy_services.entities.Provincia;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.exceptions.ResourceNotFoundException;
import team3.epic_energy_services.payloads.GeneralMessageDTO;
import team3.epic_energy_services.repositories.ComuneRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ComuneService {
    @Autowired
    private ComuneRepository comuneRepository;
    private static final String COMMA_DELIMITER = ";";

    @Autowired
    private ProvinciaService provinciaService;

    public Comune getComuneById(UUID id) {
        return comuneRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Comune non trovato con id: " + id));
    }

    public void addComune(Comune newComune) {
        Comune comune = comuneRepository.findByNomeIgnoreCase(newComune.getNome()).orElse(null);
        if (comune == null) {
            comune = newComune;
        }
        comuneRepository.save(comune);
    }

    public Page<Comune> getAllComuni(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return comuneRepository.findAll(pageable);
    }

    public GeneralMessageDTO importComuni() {

        String dir = Paths.get("").toAbsolutePath().toString();
        System.out.println(dir);
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dir + "/src/main/resources/csv/comuni.csv"))) {
            String line;
            int count = 0;
            int headerCount = 1;
            while ((line = br.readLine()) != null) {
                String[] values = line.split(COMMA_DELIMITER);
                if (count == 0) {
                    count++;
                    headerCount = values.length;
                    continue;
                }
                if (values.length == headerCount) {

                    Provincia provincia = null;
                    try {
                        provincia = provinciaService.getProvincia(values[3]);
                        this.addComune(new Comune(values[2], provincia));
                    } catch (BadRequestException e) {
                        System.err.println(e.getMessage());
                    }
                } else {
                    System.err.printf("Invalid record found at line %s. Expected %s columns but found %s columns %n", count, headerCount, values.length);
                }
            }
            return new GeneralMessageDTO("Comuni imported successfully.");
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }

    public long countComuni() {
        return comuneRepository.count();
    }
}
