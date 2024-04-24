package team3.epic_energy_services.runners;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import team3.epic_energy_services.services.ProvinciaService;

@Component
public class CSVRunner implements CommandLineRunner {
    @Autowired
    private ProvinciaService provinciaService;


    @Override
    public void run(String... args) throws Exception {

    }
}
