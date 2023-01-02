package com.inventory.app.restcontrollers;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.dto.ChangePasswordDTO;
import com.inventory.app.dto.EditOwnerDTO;
import com.inventory.app.dto.OwnerDTO;
import com.inventory.app.exceptions.BusinessRulesException;
import com.inventory.app.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

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

        if(ownerFound.isPresent()) {

            Link selfLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .getOwner(ownerFound.get().getEmail()))
                            .withSelfRel()
                            .withType("GET");

            Link collectionLink =
                    linkTo(methodOn(CollectionRestController.class)
                            .getCollection(ownerFound.get().getOwnerId()))
                            .withRel("collection")
                            .withType("GET");

            Link changeUserDetailsLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .changeUserDetails(ownerFound.get().getEmail(), null))
                            .withRel("changeUserDetails")
                            .withType("PATCH");

            Link changePasswordLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .changePassword(ownerFound.get().getEmail(), null))
                            .withRel("changePassword")
                            .withType("PATCH");

            Link deleteOwnerLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .deleteOwner(ownerFound.get().getEmail()))
                            .withRel("deleteOwner")
                            .withType("DELETE");

            ownerFound.get()
                    .add(selfLink, collectionLink, changeUserDetailsLink, changePasswordLink, deleteOwnerLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ownerFound.get());
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("");
    }

    @PostMapping(path = "", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createOwner(@RequestBody OwnerDTO ownerDTO, UriComponentsBuilder ucBuilder) {

        try{
            String token = ownerService.createOwner(Name.createName(ownerDTO.getUserName()),
                    Email.createEmail(ownerDTO.getEmail()), ownerDTO.getPassword());

            HttpHeaders location = new HttpHeaders();
            location
                    .setLocation(ucBuilder.path("/owners/{email}")
                            .buildAndExpand(ownerDTO.getEmail())
                            .toUri());

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .headers(location)
                    .body(token);

        } catch (BusinessRulesException bre) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
        }
    }

    @PatchMapping(path = "/{email}/details", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> changeUserDetails(@PathVariable(value=("email")) String email,
                                                    @RequestBody EditOwnerDTO editOwnerDTO) {

        if (ownerService.notTheSameUser(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        try {
            Owner editedOwner = ownerService.changeUserDetails(editOwnerDTO, email);

            Link selfLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .getOwner(editedOwner.getEmail()))
                            .withSelfRel()
                            .withType("GET");

            Link changePasswordLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .changePassword(editedOwner.getEmail(), null))
                            .withRel("changePassword")
                            .withType("PATCH");

            Link deleteOwnerLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .deleteOwner(editedOwner.getEmail()))
                            .withRel("deleteOwner")
                            .withType("DELETE");

            editedOwner.add(selfLink, changePasswordLink, deleteOwnerLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(editedOwner);

        } catch (BusinessRulesException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @PatchMapping(path = "/{email}/password", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> changePassword(@PathVariable(value=("email")) String email,
                                                 @RequestBody ChangePasswordDTO changePasswordDTO) {

        if (ownerService.notTheSameUser(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        try {
            Owner editedOwner = ownerService.changePassword(changePasswordDTO.getNewPassword(),
                    changePasswordDTO.getOldPassword(), changePasswordDTO.getEmail());

            Link selfLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .getOwner(editedOwner.getEmail()))
                            .withSelfRel()
                            .withType("GET");

            Link changeUserDetailsLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .changeUserDetails(editedOwner.getEmail(), null))
                            .withRel("changeUserDetails")
                            .withType("PATCH");

            Link deleteOwnerLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .deleteOwner(editedOwner.getEmail()))
                            .withRel("deleteOwner")
                            .withType("DELETE");

            editedOwner.add(selfLink, changeUserDetailsLink, deleteOwnerLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(editedOwner);

        } catch (BusinessRulesException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }

    @DeleteMapping(path = "/{email}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> deleteOwner(@PathVariable(value=("email")) String email) {

        if (ownerService.notTheSameUser(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        try {
            ownerService.deleteOwner(email);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).body("");
        } catch (NoSuchElementException nse) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(nse.getMessage());
        }
    }

    @GetMapping(path = "/confirm")
    public ResponseEntity<Object> confirmOwner(@RequestParam("token") String token) {

        try{
            String confirmed = ownerService.confirmToken(token);
            return ResponseEntity.status(HttpStatus.OK).body(confirmed);
        } catch (BusinessRulesException | NoSuchElementException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }
    }
}
