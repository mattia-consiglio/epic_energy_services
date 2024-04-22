package team3.epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Utente;
import team3.epic_energy_services.exceptions.UnauthorizedException;
import team3.epic_energy_services.payloads.JWTDTO;
import team3.epic_energy_services.payloads.LoginAuthDTO;
import team3.epic_energy_services.security.JWTTools;

@Service
public class AuthService {
    @Autowired
    private UtenteService utenteService;
    @Autowired
    private JWTTools jwtTools;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public JWTDTO login(LoginAuthDTO loginAuthDTO) {
        Utente utente = utenteService.getUtenteByUsernameOrEmail(loginAuthDTO.usernameOrEmail());
        if (utente == null || !passwordEncoder.matches(loginAuthDTO.password(), utente.getPassword())) {
            throw new UnauthorizedException("Credentials not valid. Try login again");
        }
        return new JWTDTO(jwtTools.generateToken(utente));
    }

}
