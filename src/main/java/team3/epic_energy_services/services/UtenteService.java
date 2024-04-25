package team3.epic_energy_services.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import team3.epic_energy_services.entities.Ruolo;
import team3.epic_energy_services.entities.Utente;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.payloads.UtenteDTO;
import team3.epic_energy_services.repositories.UtenteRepository;

import java.util.UUID;

@Service
public class UtenteService {
    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private RuoloService ruoloService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public Utente getUtente(UUID id) {
        return utenteRepository.findById(id).orElseThrow(() -> new BadRequestException("Utente not found"));
    }

    public Page<Utente> getUtenti(int page, int size, String sort) {
        Pageable pageable = PageRequest.of(page, size, Sort.by(sort));
        return utenteRepository.findAll(pageable);
    }

    public Utente createUtente(UtenteDTO utenteDTO) {
        if (utenteRepository.existsByUsernameOrEmail(utenteDTO.username(), utenteDTO.email())) {
            throw new BadRequestException("Username and email already in use");
        } else if (utenteRepository.existsByUsername(utenteDTO.username())) {
            throw new BadRequestException("Username already in use");
        } else if (utenteRepository.existsByEmail(utenteDTO.email())) {
            throw new BadRequestException("Email already in use");
        }
        Utente newUtente = new Utente();
        Ruolo ruolo = ruoloService.getRuolo("USER");
        newUtente.setEmail(utenteDTO.email());
        newUtente.setPassword(passwordEncoder.encode(utenteDTO.password()));
        newUtente.setNome(utenteDTO.nome());
        newUtente.setCognome(utenteDTO.cognome());
        newUtente.setRuolo(ruolo);
        newUtente.setUsername(utenteDTO.username());
        String avatarUrl = "https://ui-avatars.com/api/?name=" + utenteDTO.nome().charAt(0) + "+" + utenteDTO.cognome().charAt(0);
        newUtente.setAvatarUrl(avatarUrl);
        return utenteRepository.save(newUtente);
    }

    public Utente createUtente(UtenteDTO utenteDTO, String ruoloString) {
        if (utenteRepository.existsByUsernameOrEmail(utenteDTO.username(), utenteDTO.email())) {
            throw new BadRequestException("Username and email already in use");
        } else if (utenteRepository.existsByUsername(utenteDTO.username())) {
            throw new BadRequestException("Username already in use");
        } else if (utenteRepository.existsByEmail(utenteDTO.email())) {
            throw new BadRequestException("Email already in use");
        }

        Utente newUtente = new Utente();

        Ruolo ruolo = ruoloService.getRuolo(ruoloString);
        newUtente.setEmail(utenteDTO.email());
        newUtente.setPassword(passwordEncoder.encode(utenteDTO.password()));
        newUtente.setNome(utenteDTO.nome());
        newUtente.setCognome(utenteDTO.cognome());
        newUtente.setRuolo(ruolo);
        newUtente.setUsername(utenteDTO.username());
        String avatarUrl = "https://ui-avatars.com/api/?name=" + utenteDTO.nome().charAt(0) + "+" + utenteDTO.cognome().charAt(0);
        newUtente.setAvatarUrl(avatarUrl);
        return utenteRepository.save(newUtente);
    }

    public Utente getUtenteByUsernameOrEmail(String userOrEmail) {
        return utenteRepository.findByUsernameOrEmail(userOrEmail, userOrEmail).orElseThrow(() -> new BadRequestException("Utente not found"));
    }

    public boolean existsByUsernameOrEmail(String userOrEmail) {
        return utenteRepository.existsByUsernameOrEmail(userOrEmail, userOrEmail);
    }

    public boolean existsByUsername(String username) {
        return utenteRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return utenteRepository.existsByEmail(email);
    }

    public void deleteUtente(UUID id) {
        utenteRepository.deleteById(id);
    }

    public Utente updateUtente(UUID id, UtenteDTO utenteDTO) {
        Utente existingUtente = utenteRepository.findById(id).orElseThrow(() -> new BadRequestException("Utente not found"));
        if (utenteRepository.existsByUsernameOrEmail(utenteDTO.username(), utenteDTO.email()) && !existingUtente.getUsername().equals(utenteDTO.username()) && !existingUtente.getEmail().equals(utenteDTO.email())) {
            throw new BadRequestException("Username and email already in use");
        } else if (utenteRepository.existsByUsername(utenteDTO.username()) && !existingUtente.getUsername().equals(utenteDTO.username())) {
            throw new BadRequestException("Username already in use");
        } else if (utenteRepository.existsByEmail(utenteDTO.email()) && !existingUtente.getEmail().equals(utenteDTO.email())) {
            throw new BadRequestException("Email already in use");
        }
        String avatarUrl = "https://ui-avatars.com/api/?name=" + utenteDTO.nome().charAt(0) + "+" + utenteDTO.cognome().charAt(0);
        existingUtente.setNome(utenteDTO.nome());
        existingUtente.setCognome(utenteDTO.cognome());
        existingUtente.setPassword(passwordEncoder.encode(utenteDTO.password()));
        existingUtente.setEmail(utenteDTO.email());
        existingUtente.setUsername(utenteDTO.username());
        existingUtente.setAvatarUrl(avatarUrl);

        return utenteRepository.save(existingUtente);
    }
}
