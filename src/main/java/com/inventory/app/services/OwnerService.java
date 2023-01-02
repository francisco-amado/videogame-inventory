package com.inventory.app.services;

import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.factories.ConfirmationTokenFactoryInterface;
import com.inventory.app.domain.factories.OwnerFactoryInterface;
import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.owner.OwnerRole;
import com.inventory.app.domain.token.ConfirmationToken;
import com.inventory.app.domain.valueobjects.*;
import com.inventory.app.dto.EditOwnerDTO;
import com.inventory.app.dto.OwnerDTO;
import com.inventory.app.email.EmailSender;
import com.inventory.app.exceptions.BusinessRulesException;
import com.inventory.app.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Service
public class OwnerService implements UserDetailsService {

    private final OwnerRepository ownerRepository;
    private final OwnerFactoryInterface ownerFactoryInterface;
    private final ConfirmationTokenFactoryInterface confirmationTokenFactoryInterface;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailSender emailSender;
    private final static String USER_NOT_FOUND = "User with email %s not found";
    private final static String TOKEN_RESPONSE = "Confirmed";
    private final static String EMAIL_LINK = "http://localhost:8080/api/v1/owners/confirm?token=";

    @Autowired
    public OwnerService(OwnerRepository ownerRepository,
                        OwnerFactoryInterface ownerFactoryInterface,
                        ConfirmationTokenFactoryInterface confirmationTokenFactoryInterface,
                        BCryptPasswordEncoder bCryptPasswordEncoder,
                        ConfirmationTokenService confirmationTokenService,
                        EmailSender emailSender) {

        this.ownerRepository = ownerRepository;
        this.ownerFactoryInterface = ownerFactoryInterface;
        this.confirmationTokenFactoryInterface = confirmationTokenFactoryInterface;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.emailSender = emailSender;
    }

    @Transactional
    public String createOwner(Name userName, String email, String password) throws BusinessRulesException {

        boolean validDetails = validateOwnerDetails(userName, email, password);

        if(!validDetails) {
            throw new BusinessRulesException("Invalid user details");
        }

        String encodedPassword =  bCryptPasswordEncoder.encode(password);
        Owner newOwner = ownerFactoryInterface.createOwner(userName, email, password);
        newOwner.setOwnerRole(OwnerRole.USER);
        newOwner.setPassword(encodedPassword);
        ownerRepository.save(newOwner);
        String token = createConfirmationToken(newOwner).getToken();
        sendEmail(token, userName, email);

        return token;
    }

    public boolean validateOwnerDetails(Name userName, String email, String password) {

        boolean valid = !existsByEmail(email) && !existsByUsername(userName);
        if(!valid) return false;

        return password != null && password.length() >= 10;
    }

    public ConfirmationToken createConfirmationToken(Owner newOwner) {

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = confirmationTokenFactoryInterface.createConfirmationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), newOwner);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return confirmationToken;
    }

    public void sendEmail(String token, Name userName, String email) {
        String link = EMAIL_LINK + token;
        emailSender.send(email, emailSender.buildEmail(userName.getName(), link));
    }

    public boolean existsByUsername(Name userName) {
        return ownerRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String email) {
        return ownerRepository.existsByEmail(email);
    }

    public Optional<Owner> findByEmail(String email) {
        return ownerRepository.findByEmail(email);
    }

    public Optional<Owner> findById(UUID ownerId) {
        return ownerRepository.findById(ownerId);
    }

    public void enableOwner(String email) {
        ownerRepository.enableOwner(email);
    }

    @Transactional
    public String confirmToken(String token) throws BusinessRulesException, NoSuchElementException {

        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                        .orElseThrow(() -> new NoSuchElementException("Token not found"));

        if (confirmationToken.getConfirmed() != null) {
            throw new BusinessRulesException("E-mail already confirmed");
        }

        LocalDateTime expired = confirmationToken.getExpires();

        if (expired.isBefore(LocalDateTime.now())) {
            throw new BusinessRulesException("Token expired");
        }

        confirmationTokenService.setConfirmed(token);
        enableOwner(confirmationToken.getOwner().getEmail());

        return TOKEN_RESPONSE;
    }

    public Owner changeUserDetails(EditOwnerDTO editOwnerDTO) throws BusinessRulesException, NoSuchElementException {

        boolean validDetails = !existsByEmail(Email.createEmail(editOwnerDTO.getEmail())) &&
                !existsByUsername(Name.createName(editOwnerDTO.getUserName()));

        if (!validDetails) throw new BusinessRulesException("Invalid user details");

        Optional<Owner> ownerToEdit = ownerRepository.findByEmail(editOwnerDTO.getEmail());

        if (ownerToEdit.isEmpty()) {
            throw new NoSuchElementException("The requested owner does not exist");
        } else {

            if (editOwnerDTO.getEmail() != null && !Objects.equals(editOwnerDTO.getEmail(),
                    ownerToEdit.get().getEmail())) {

                ownerToEdit.get().setEmail(editOwnerDTO.getEmail());
            }

            if (editOwnerDTO.getUserName() != null && !Objects.equals(editOwnerDTO.getUserName(),
                    ownerToEdit.get().getUsername())) {

                ownerToEdit.get().setUserName(Name.createName(editOwnerDTO.getUserName()));
            }

            ownerRepository.save(ownerToEdit.get());
        }

        return ownerToEdit.get();
    }

    public void deleteOwner(String email) throws NoSuchElementException {
        Optional<Owner> ownerToDelete = ownerRepository.findByEmail(email);
        ownerRepository.delete(ownerToDelete
                .orElseThrow(() -> new NoSuchElementException("User not found")));
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }
}
