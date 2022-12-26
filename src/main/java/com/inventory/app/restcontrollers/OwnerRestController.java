package com.inventory.app.restcontrollers;

import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.dto.OwnerDTO;
import com.inventory.app.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/owners")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OwnerRestController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerRestController(OwnerService ownerService) {
        this.ownerService = ownerService;
    }

    @PostMapping(path = "/create", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createOwner(@RequestBody OwnerDTO ownerDTO) {

        try{
            String token = ownerService.createOwner(Name.createName(ownerDTO.getUserName()),
                    Email.createEmail(ownerDTO.getEmail()), ownerDTO.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(token);
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
