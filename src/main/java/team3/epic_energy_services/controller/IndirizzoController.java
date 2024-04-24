package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team3.epic_energy_services.entities.Indirizzo;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.payloads.IndirizzoDTO;
import team3.epic_energy_services.services.IndirizzoService;

import java.util.UUID;

@RestController
@RequestMapping("api/indirizzi")
public class IndirizzoController {

    @Autowired
    private IndirizzoService indirizzoService;

    @GetMapping
    public Page<Indirizzo> getAllIndirizzo(@RequestParam(defaultValue = "0") int page,
                                           @RequestParam(defaultValue = "10") int size,
                                           @RequestParam(defaultValue = "id") String sort) {
        return indirizzoService.getIndirizzi(page, size, sort);
    }

    @GetMapping("/{id}")
    public Indirizzo getIndirizzoById(@PathVariable UUID id) {
        return indirizzoService.findById(id);
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public Indirizzo saveNewIndirizzo(@RequestBody @Validated IndirizzoDTO payload, BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException("Invlaid data", validation.getAllErrors());
        return indirizzoService.add(payload);
    }

    @PostMapping("test")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public String test(@RequestBody @Validated IndirizzoDTO payload, BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException("Invlaid data", validation.getAllErrors());
        return testService.test();
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteIndirizzo(@PathVariable UUID id) {
        indirizzoService.findByIdAndDelete(id);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public Indirizzo updateDevice(@PathVariable UUID id, @RequestBody @Validated IndirizzoDTO payload, BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException("invlaid data", validation.getAllErrors());
        else return indirizzoService.findByIdAndUpdate(id, payload);
    }
}
