package team3.epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Ruolo;
import team3.epic_energy_services.entities.Utente;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.payloads.UtenteDTO;
import team3.epic_energy_services.repositories.UtentiInterface;

import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtentiInterface utentiInterface;

    @Autowired
    private RuoloService ruoloService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utente getUtente(UUID id) {
        return utentiInterface.findById(id).orElseThrow(() -> new BadRequestException("Utente not found"));
    }

    public Utente createUtente(UtenteDTO utente) {
        Utente newUtente = new Utente();
        Ruolo ruolo = ruoloService.getRuolo("USER");
        newUtente.setEmail(utente.email());
        newUtente.setPassword(passwordEncoder.encode(utente.password()));
        newUtente.setNome(utente.nome());
        newUtente.setCognome(utente.cognome());
        newUtente.setRuolo(ruolo);
        newUtente.setUsername(utente.username());
        String avatarUrl = "https://ui-avatars.com/api/?name=" + utente.nome().charAt(0) + "+" + utente.cognome().charAt(0);
        newUtente.setAvatarUrl(avatarUrl);
        return utentiInterface.save(newUtente);
    }

    public Utente createUtente(UtenteDTO utente, String ruoloString) {
        Utente newUtente = new Utente();
        Ruolo ruolo = ruoloService.getRuolo(ruoloString);
        newUtente.setEmail(utente.email());
        newUtente.setPassword(passwordEncoder.encode(utente.password()));
        newUtente.setNome(utente.nome());
        newUtente.setCognome(utente.cognome());
        newUtente.setRuolo(ruolo);
        newUtente.setUsername(utente.username());
        String avatarUrl = "https://ui-avatars.com/api/?name=" + utente.nome().charAt(0) + "+" + utente.cognome().charAt(0);
        newUtente.setAvatarUrl(avatarUrl);
        return utentiInterface.save(newUtente);
    }

    public Utente getUtenteByUsernameOrEmail(String userOrEmail) {
        return utentiInterface.findByUsernameOrEmail(userOrEmail, userOrEmail).orElseThrow(() -> new BadRequestException("Utente not found"));
    }

    public boolean existsByUsernameOrEmail(String userOrEmail) {
        return utentiInterface.existsByUsernameOrEmail(userOrEmail, userOrEmail);
    }

    public boolean existsByUsername(String username) {
        return utentiInterface.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return utentiInterface.existsByEmail(email);
    }

    public void deleteUtente(UUID id) {
        utentiInterface.deleteById(id);
    }

    public Utente updateUtente(UUID id, UtenteDTO utenteDTO) {
        Utente existingUtente = utentiInterface.findById(id).orElseThrow(() -> new BadRequestException("Utente not found"));



        return utentiInterface.save(existingUtente);
    }
}
