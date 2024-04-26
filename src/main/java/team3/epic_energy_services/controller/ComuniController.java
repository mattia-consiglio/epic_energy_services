package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import team3.epic_energy_services.entities.Comune;
import team3.epic_energy_services.services.ComuneService;

@RestController
@RequestMapping("api/comuni")
public class ComuniController {

    @Autowired
    private ComuneService comuneService;

    @GetMapping
    public Page<Comune> getComuni(@RequestParam(defaultValue = "0") int page, @RequestParam(defaultValue = "10") int size, @RequestParam(defaultValue = "nome") String sort) {
        return comuneService.getAllComuni(page, size, sort);
    }
}
