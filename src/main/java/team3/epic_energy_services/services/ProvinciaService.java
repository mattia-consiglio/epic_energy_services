package team3.epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Provincia;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.payloads.GeneralMessageDTO;
import team3.epic_energy_services.repositories.ProvinciaRepository;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProvinciaService {
    @Autowired
    private ProvinciaRepository provinciaRepository;
    private static final String COMMA_DELIMITER = ";";


    public void addProvincia(Provincia newProvincia) {
        Provincia provincia = provinciaRepository.findByNomeIgnoreCase(newProvincia.getNome()).orElse(null);
        if (provincia == null) {
            provincia = newProvincia;
        }
        provinciaRepository.save(provincia);
    }

    public Provincia getProvincia(String nome) {
        Provincia provincia = provinciaRepository.findByNomeIgnoreCase(nome).orElse(null);
        if (provincia == null) {
            throw new BadRequestException("Provincia not found: " + nome);
        }
        return provincia;
    }


    public List<Provincia> getAllProvince() {
        return provinciaRepository.findAll();
    }


    public GeneralMessageDTO importProvince() {

        String dir = Paths.get("").toAbsolutePath().toString();
        List<List<String>> records = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(dir + "/src/main/resources/csv/province.csv"))) {
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
                    this.addProvincia(new Provincia(values[0], values[1], values[2]));
                } else {
                    System.err.printf("Invalid record found at line %s. Expected %s columns but found %s columns %n", count, headerCount, values.length);
                }
            }
            return new GeneralMessageDTO("Province imported successfully");
        } catch (IOException e) {
            throw new BadRequestException(e.getMessage());
        }
    }
}
