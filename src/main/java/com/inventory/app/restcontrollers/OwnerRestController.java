package com.inventory.app.restcontrollers;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.dto.*;
import com.inventory.app.dto.mappers.OwnerDTOMapper;
import com.inventory.app.exceptions.BusinessRulesException;
import com.inventory.app.services.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.persistence.OneToOne;
import java.util.NoSuchElementException;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/owners")
@CrossOrigin(origins = "*", maxAge = 3600)
public class OwnerRestController {

    private final OwnerService ownerService;
    private final OwnerDTOMapper ownerDTOMapper;

    @Autowired
    public OwnerRestController(OwnerService ownerService, OwnerDTOMapper ownerDTOMapper) {
        this.ownerService = ownerService;
        this.ownerDTOMapper = ownerDTOMapper;
    }

    @GetMapping(path = "/{email}", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> getOwner(@PathVariable(value=("email")) String email) {

        Optional<Owner> ownerFound = ownerService.findByEmail(email);

        if(ownerFound.isPresent()) {

            OwnerDTO ownerDTO = ownerDTOMapper.toDTO(ownerFound.get());

            Link selfLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .getOwner(ownerDTO.getEmail()))
                            .withSelfRel()
                            .withType("GET");

            Link createCollectionLink =
                    linkTo(methodOn(CollectionRestController.class)
                            .createCollection(ownerDTO.getOwnerId(), null))
                            .withRel("createCollection")
                            .withType("POST");

            Link changeOwnerDetailsLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .changeOwnerDetails(ownerDTO.getEmail(), null))
                            .withRel("changeOwnerDetails")
                            .withType("PATCH");

            Link changePasswordLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .changePassword(ownerDTO.getEmail(), null))
                            .withRel("changePassword")
                            .withType("PATCH");

            Link deleteOwnerLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .deleteOwner(ownerDTO.getEmail()))
                            .withRel("deleteOwner")
                            .withType("DELETE");

            ownerDTO.add(selfLink, createCollectionLink, changeOwnerDetailsLink, changePasswordLink, deleteOwnerLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ownerDTO);
        }

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body("");
    }

    @PostMapping(path = "", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> createOwner(@RequestBody CreateOwnerDTO createOwnerDTO,
                                              UriComponentsBuilder ucBuilder) {

        try{
            String token = ownerService.createOwner(Name.createName(createOwnerDTO.getUserName()),
                    Email.createEmail(createOwnerDTO.getEmail()), createOwnerDTO.getPassword());

            HttpHeaders location = new HttpHeaders();
            location
                    .setLocation(ucBuilder.path("/owners/{email}")
                            .buildAndExpand(createOwnerDTO.getEmail())
                            .toUri());

            Link ownerLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .getOwner(createOwnerDTO.getEmail()))
                            .withRel("getOwner")
                            .withType("GET");

            TokenDTO tokenDTO = new TokenDTO();
            tokenDTO.setToken(token);
            tokenDTO.add(ownerLink);

            return ResponseEntity
                    .status(HttpStatus.CREATED)
                    .headers(location)
                    .body(tokenDTO);

        } catch (BusinessRulesException bre) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(bre.getMessage());
        }
    }

    @PatchMapping(path = "/{email}/details", headers = "Accept=application/json", produces = "application/json")
    public ResponseEntity<Object> changeOwnerDetails(@PathVariable(value=("email")) String email,
                                                     @RequestBody EditOwnerDTO editOwnerDTO) {

        if (ownerService.notTheSameUser(email)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("");
        }

        try {
            Owner editedOwner = ownerService.changeOwnerDetails(editOwnerDTO, email);

            OwnerDTO ownerDTO = ownerDTOMapper.toDTO(editedOwner);

            Link selfLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .getOwner(ownerDTO.getEmail()))
                            .withSelfRel()
                            .withType("GET");

            Link changePasswordLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .changePassword(ownerDTO.getEmail(), null))
                            .withRel("changePassword")
                            .withType("PATCH");

            Link deleteOwnerLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .deleteOwner(ownerDTO.getEmail()))
                            .withRel("deleteOwner")
                            .withType("DELETE");

            ownerDTO.add(selfLink, changePasswordLink, deleteOwnerLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ownerDTO);

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

            OwnerDTO ownerDTO = ownerDTOMapper.toDTO(editedOwner);

            Link selfLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .getOwner(ownerDTO.getEmail()))
                            .withSelfRel()
                            .withType("GET");

            Link changeOwnerDetailsLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .changeOwnerDetails(ownerDTO.getEmail(), null))
                            .withRel("changeOwnerDetails")
                            .withType("PATCH");

            Link deleteOwnerLink =
                    linkTo(methodOn(OwnerRestController.class)
                            .deleteOwner(ownerDTO.getEmail()))
                            .withRel("deleteOwner")
                            .withType("DELETE");

            ownerDTO.add(selfLink, changeOwnerDetailsLink, deleteOwnerLink);

            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(ownerDTO);

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
