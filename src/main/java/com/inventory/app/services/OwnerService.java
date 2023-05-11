package com.inventory.app.services;

import com.inventory.app.domain.confirmationtoken.ConfirmationToken;
import com.inventory.app.domain.factories.ConfirmationTokenFactory;
import com.inventory.app.domain.factories.OwnerFactory;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.valueobjects.OwnerRole;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.dto.EditOwnerDTO;
import com.inventory.app.email.EmailSender;
import com.inventory.app.exceptions.BusinessRulesException;
import com.inventory.app.repositories.OwnerRepository;
import com.inventory.app.utils.ServiceResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.*;

@Service
public class OwnerService implements UserDetailsService {

    private final OwnerRepository ownerRepository;
    private final OwnerFactory ownerFactoryInterface;
    private final ConfirmationTokenFactory confirmationTokenFactory;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final CollectionService collectionService;
    private final EmailSender emailSender;

    @Autowired
    public OwnerService(OwnerRepository ownerRepository,
                        OwnerFactory ownerFactoryInterface,
                        ConfirmationTokenFactory confirmationTokenFactory,
                        BCryptPasswordEncoder bCryptPasswordEncoder,
                        ConfirmationTokenService confirmationTokenService,
                        CollectionService collectionService, EmailSender emailSender) {

        this.ownerRepository = ownerRepository;
        this.ownerFactoryInterface = ownerFactoryInterface;
        this.confirmationTokenFactory = confirmationTokenFactory;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
        this.collectionService = collectionService;
        this.emailSender = emailSender;
    }

    @Transactional
    public String createOwner(Name userName, String email, String password, String confirmPassword)
            throws BusinessRulesException {

        boolean validDetails = validateOwnerDetails(userName, email);
        boolean validPassword = validatePassword(password, confirmPassword, null);

        if(!validDetails) {
            throw new BusinessRulesException(ServiceResponses.getINVALID_USER_DETAILS());
        }

        if(!validPassword) {
            throw new BusinessRulesException(ServiceResponses.getINVALID_PASSWORD());
        }

        String newPassword = password.trim();
        String encodedPassword = bCryptPasswordEncoder.encode(newPassword);
        Owner newOwner = ownerFactoryInterface.createOwner(userName, email.trim(), newPassword);
        newOwner.setOwnerRole(OwnerRole.USER);
        newOwner.setPassword(encodedPassword);
        ownerRepository.save(newOwner);
        collectionService.createCollection(newOwner);
        String token = createConfirmationToken(newOwner).getToken();
        sendEmail(token, userName, email);

        return token;
    }

    public boolean validateOwnerDetails(Name userName, String email) {
        return notExistsByUsername(userName) && !existsByEmail(email);
    }

    public boolean validateUsername(Name userName) {
        return notExistsByUsername(userName);
    }

    public boolean validatePassword(String newPassword, String confirmPassword, String oldPassword) {

        int minPasswordLength = 10;
        int maxPasswordLength = 25;

        if(oldPassword == null) {
            return newPassword != null &&
                    Objects.equals(newPassword, confirmPassword) &&
                    newPassword.length() >= minPasswordLength &&
                    newPassword.length() <= maxPasswordLength;
        } else {
            return newPassword != null &&
                    Objects.equals(newPassword, confirmPassword) &&
                    !Objects.equals(newPassword, oldPassword) &&
                    newPassword.length() >= minPasswordLength &&
                    newPassword.length() <= maxPasswordLength;
        }
    }

    public boolean validateOldPassword(String oldPassword, String email) {

        Optional<Owner> ownerToEdit = findByEmail(email);
        String password = "";

        if(ownerToEdit.isPresent()) {
           password = ownerToEdit.get().getPassword();
        }

        return bCryptPasswordEncoder.matches(oldPassword, password);
    }


    public ConfirmationToken createConfirmationToken(Owner newOwner) {

        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = confirmationTokenFactory.createConfirmationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), newOwner);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return confirmationToken;
    }

    public void sendEmail(String token, Name userName, String email) {
        String link = ServiceResponses.getEMAIL_LINK() + token;
        emailSender.send(email, emailSender.buildEmail(userName.getName(), link));
    }

    public boolean notExistsByUsername(Name userName) {
        return !ownerRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(String email) {
        return ownerRepository.existsByEmail(email);
    }

    public Optional<Owner> findByEmail(String email) {
        return ownerRepository.findByEmail(email);
    }

    public void enableOwner(String email) {
        ownerRepository.enableOwner(email);
    }

    @Transactional
    public String confirmToken(String token) throws BusinessRulesException, NoSuchElementException {

        ConfirmationToken confirmationToken = confirmationTokenService.getToken(token)
                        .orElseThrow(() -> new NoSuchElementException(ServiceResponses.getTOKEN_NOT_FOUND()));

        if (confirmationToken.getConfirmed() != null) {
            throw new BusinessRulesException(ServiceResponses.getEMAIL_CONFIRMED());
        }

        LocalDateTime expired = confirmationToken.getExpires();

        if (expired.isBefore(LocalDateTime.now())) {
            throw new BusinessRulesException(ServiceResponses.getTOKEN_EXPIRED());
        }

        confirmationTokenService.setConfirmed(token);
        enableOwner(confirmationToken.getOwner().getEmail());

        return ServiceResponses.getTOKEN_RESPONSE();
    }

    public void validateOwnerDetailsToChange(EditOwnerDTO editOwnerDTO) {

        String username = "";

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        if (editOwnerDTO == null) {
            throw new BusinessRulesException(ServiceResponses.getINVALID_ENTRY_DATA());
        }

        if(editOwnerDTO.getUserName() != null) {

           if (username.equals(editOwnerDTO.getUserName())) {
                throw new BusinessRulesException(ServiceResponses.getINVALID_ENTRY_DATA());
           }

           if (!validateUsername(Name.createName(editOwnerDTO.getUserName()))) {
               throw new BusinessRulesException(ServiceResponses.getINVALID_ENTRY_DATA());
           }
        }
    }

    public Owner changeOwnerDetails(EditOwnerDTO editOwnerDTO, String email)
            throws BusinessRulesException, NoSuchElementException {

        validateOwnerDetailsToChange(editOwnerDTO);

        Optional<Owner> ownerToEdit = findByEmail(email);

        if (ownerToEdit.isEmpty()) {
            throw new NoSuchElementException(ServiceResponses.getOWNER_NOT_FOUND());
        } else {

            if (editOwnerDTO.getUserName() != null && !Objects.equals(editOwnerDTO.getUserName(),
                    ownerToEdit.get().getUsername())) {

                ownerToEdit.get().setUserName(Name.createName(editOwnerDTO.getUserName()));
            }

            ownerRepository.save(ownerToEdit.get());
        }

        return ownerToEdit.get();
    }

    public Owner changePassword(String newPassword, String oldPassword, String confirmPassword, String email) {

        if (oldPassword == null) {
            throw new BusinessRulesException(ServiceResponses.getINVALID_PASSWORD());
        }

        if(!validateOldPassword(oldPassword, email)) {
            throw new BusinessRulesException(ServiceResponses.getINVALID_PASSWORD());
        }

        if(!validatePassword(newPassword, confirmPassword, oldPassword)) {
            throw new BusinessRulesException(ServiceResponses.getINVALID_PASSWORD());
        }

        Optional<Owner> ownerToEdit = findByEmail(email);

        if(ownerToEdit.isPresent()) {
            String encodedPassword = bCryptPasswordEncoder.encode(newPassword.trim());
            ownerToEdit.get().setPassword(encodedPassword);
            ownerRepository.save(ownerToEdit.get());
            return ownerToEdit.get();
        } else {
            throw new NoSuchElementException(ServiceResponses.getOWNER_NOT_FOUND());
        }
    }

    @Transactional
    public void deleteOwner(String email) throws NoSuchElementException {

        Optional<Owner> ownerToDelete = findByEmail(email);

        if(ownerToDelete.isPresent()) {
            Optional<ConfirmationToken> ownerToken = confirmationTokenService.findByOwner(ownerToDelete.get());
            collectionService.deleteCollection(ownerToDelete.get().getCollection().getCollectionId());
            ownerToken.ifPresent(confirmationTokenService::delete);
            ownerToDelete.ifPresent(ownerRepository::delete);
        } else {
            throw new NoSuchElementException(ServiceResponses.getOWNER_NOT_FOUND());
        }
    }

    public boolean notTheSameUser(String email) {

        String username = "";

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            username = SecurityContextHolder.getContext().getAuthentication().getName();
        }

        Owner owner = findByEmail(email)
                .orElseThrow(() -> new NoSuchElementException(ServiceResponses.getOWNER_NOT_FOUND()));

        return !username.equals(owner.getUsername());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException(ServiceResponses.getOWNER_NOT_FOUND()));
    }
}
