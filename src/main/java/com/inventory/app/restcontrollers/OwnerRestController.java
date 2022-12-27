package com.inventory.app.restcontrollers;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.dto.OwnerDTO;
import com.inventory.app.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.swing.text.html.Option;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/owners")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OwnerRestController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerRestController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @GetMapping(path = "/{email}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> getOwner(@PathVariable(value=("email")) String email) {

        Optional<Owner> ownerFound = ownerService.findByEmail(email);

        return ownerFound.<ResponseEntity<Object>>map(
                owner -> ResponseEntity
                        .status(HttpStatus.OK)
                        .body(owner))
                .orElseGet(() -> ResponseEntity
                        .status(HttpStatus.NOT_FOUND)
                        .body("Owner does not exist"));
    }

    @PostMapping(path = "", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createOwner(@RequestBody OwnerDTO ownerDTO, UriComponentsBuilder ucBuilder) {

        try{
            String token = ownerService.createOwner(Name.createName(ownerDTO.getUserName()),
                    Email.createEmail(ownerDTO.getEmail()), ownerDTO.getPassword());

            HttpHeaders headers = new HttpHeaders();
            headers
                    .setLocation(ucBuilder.path("/owners/{email}")
                            .buildAndExpand(ownerDTO.getEmail())
                            .toUri());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .headers(headers)
                    .body(token);

        } catch (IllegalStateException ise) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ise.getMessage());
        }
    }

    @GetMapping(path = "/confirm")
    public ResponseEntity<Object> confirmOwner(@RequestParam("token") String token) {

        try{
            String confirmed = ownerService.confirmToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(confirmed);
        } catch (IllegalStateException ise) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ise.getMessage());
        }
    }
}
