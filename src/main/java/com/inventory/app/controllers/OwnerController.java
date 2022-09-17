package com.inventory.app.controllers;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


public class OwnerController {

    private final OwnerService ownerService;

    @Autowired
    public OwnerController(OwnerService ownerService) {

        this.ownerService = ownerService;
    }

//    @PostMapping("/create")
//    public ResponseEntity<Object> createOwner(@RequestBody Owner) {
//
//        if (ownerService.existsByEmail(Owner)) {
//
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Owner already has a collection");
//        }
//
//        ownerService.createOwner(Owner);
//
//        return ResponseEntity.status(HttpStatus.CREATED).body("Collection created successfully");
//    }
}
