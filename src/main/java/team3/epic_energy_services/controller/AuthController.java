package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team3.epic_energy_services.entities.Utente;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.payloads.JWTDTO;
import team3.epic_energy_services.payloads.LoginAuthDTO;
import team3.epic_energy_services.payloads.UtenteDTO;
import team3.epic_energy_services.services.AuthService;
import team3.epic_energy_services.services.UtenteService;

@RestController
@RequestMapping("api/auth")
public class AuthController {
    @Autowired
    private AuthService authService;
    @Autowired
    private UtenteService utenteService;

    @PostMapping("login")
    public JWTDTO login(@RequestBody @Validated LoginAuthDTO loginAuthDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return authService.login(loginAuthDTO);
    }


    @PostMapping("register")
    @ResponseStatus(HttpStatus.CREATED)
    public Utente createUtente(@RequestBody @Validated UtenteDTO userDTO, BindingResult validation) {
        if (validation.hasErrors()) {
            throw new BadRequestException("Invalid data", validation.getAllErrors());
        }
        return utenteService.createUtente(userDTO);
    }
}
