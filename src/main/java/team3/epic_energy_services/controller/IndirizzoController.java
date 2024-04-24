package team3.epic_energy_services.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import team3.epic_energy_services.entities.Indirizzo;
import team3.epic_energy_services.exceptions.BadRequestException;
import team3.epic_energy_services.payloads.IndirizzoDTO;
import team3.epic_energy_services.services.IndirizzoService;

import java.util.UUID;

@RestController
@RequestMapping("/address")
public class IndirizzoController {

   @Autowired
   private IndirizzoService iS;

    @GetMapping
    private Page<Indirizzo> getAllIndirizzo(@RequestParam(defaultValue = "0") int page,
                                            @RequestParam(defaultValue = "10") int size,
                                            @RequestParam(defaultValue = "id") String sort) {
        return iS.getIndirizzo(page, size, sort);
    }

    @GetMapping("/{id}")
    private Indirizzo getIndirizzoById(@PathVariable UUID id) {
        return iS.findById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private Indirizzo saveNewIndirizzo(@RequestBody @Validated IndirizzoDTO payload, BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException("invlaid data", validation.getAllErrors());
        else return iS.save(payload);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private void deleteIndirizzo(@PathVariable UUID id) {
        iS.findByIdAndDelete(id);
    }

    @PutMapping("/{id}")
    private Indirizzo updateDevice(@PathVariable UUID id, @RequestBody @Validated IndirizzoDTO payload, BindingResult validation) {
        if (validation.hasErrors()) throw new BadRequestException("invlaid data", validation.getAllErrors());
        else return iS.findByIdAndUpdate(id, payload);
    }
}
