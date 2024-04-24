package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import team3.epic_energy_services.payloads.GeneralMessageDTO;
import team3.epic_energy_services.services.ComuneService;
import team3.epic_energy_services.services.ProvinciaService;

@RestController
@RequestMapping("api/import")
public class ImportController {
    @Autowired
    private ProvinciaService provinciaService;

    @Autowired
    private ComuneService comuneService;

    @GetMapping("province")
    @PreAuthorize("hasAuthority('ADMIN')")
    public GeneralMessageDTO importProvincie() {
        return provinciaService.importProvince();
    }

    @GetMapping("comuni")
    @PreAuthorize("hasAuthority('ADMIN')")
    public GeneralMessageDTO importComuni() {
        return comuneService.importComuni();
    }
}
