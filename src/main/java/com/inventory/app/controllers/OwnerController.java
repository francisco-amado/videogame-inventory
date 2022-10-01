package com.inventory.app.controllers;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.OwnerId;
import com.inventory.app.domain.valueobjects.Password;
import com.inventory.app.dto.OwnerDTO;
import com.inventory.app.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/owners")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {

        this.ownerService = ownerService;
    }

    @PostMapping(path = "/create", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createCollection(@RequestBody OwnerDTO ownerDTO) {

        if (ownerService.existsByEmail(Email.createEmail(ownerDTO.email)) ||
                ownerService.existsByUsername(Name.createName(ownerDTO.userName))) {

            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Owner already exists");
        }

        ownerService.createOwner(Name.createName(ownerDTO.userName), Email.createEmail(ownerDTO.email),
                Password.createPassword(ownerDTO.password));

        return ResponseEntity.status(HttpStatus.CREATED).body("Owner created successfully");
    }
}
