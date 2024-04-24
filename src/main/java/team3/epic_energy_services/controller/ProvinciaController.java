package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team3.epic_energy_services.payloads.GeneralMessageDTO;
import team3.epic_energy_services.services.ProvinciaService;

@RestController
@RequestMapping("api/import")
public class ProvinciaController {
    @Autowired
    private ProvinciaService provinciaService;

    @GetMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public GeneralMessageDTO importAll() {
        return provinciaService.importProvince();
    }

    @GetMapping("provincie")
    @PreAuthorize("hasAuthority('ADMIN')")
    public GeneralMessageDTO importProvincie() {
        return provinciaService.importProvince();
    }
}
