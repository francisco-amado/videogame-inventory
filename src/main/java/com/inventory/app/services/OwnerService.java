package com.inventory.app.services;

import com.inventory.app.domain.factories.ConfirmationTokenFactoryInterface;
import com.inventory.app.domain.factories.OwnerFactoryInterface;
import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.owner.OwnerRole;
import com.inventory.app.domain.token.ConfirmationToken;
import com.inventory.app.domain.valueobjects.*;
import com.inventory.app.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class OwnerService implements UserDetailsService {

    private final OwnerRepository ownerRepository;
    private final OwnerFactoryInterface ownerFactoryInterface;
    private final ConfirmationTokenFactoryInterface confirmationTokenFactoryInterface;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final static String USER_NOT_FOUND = "User with email %s not found";

    @Autowired
    public OwnerService(OwnerRepository ownerRepository, OwnerFactoryInterface ownerFactoryInterface,
                        ConfirmationTokenFactoryInterface confirmationTokenFactoryInterface,
                        BCryptPasswordEncoder bCryptPasswordEncoder, ConfirmationTokenService confirmationTokenService) {

        this.ownerRepository = ownerRepository;
        this.ownerFactoryInterface = ownerFactoryInterface;
        this.confirmationTokenFactoryInterface = confirmationTokenFactoryInterface;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.confirmationTokenService = confirmationTokenService;
    }

    public String createOwner(Name userName, Email email, String password) throws IllegalStateException {

        if (existsByEmail(email) || existsByUsername(userName)) {
            throw new IllegalStateException("Owner already exists");
        }

        String encodedPassword =  bCryptPasswordEncoder.encode(password);
        Owner newOwner = ownerFactoryInterface.createOwner(userName, email, password);
        newOwner.setOwnerRole(OwnerRole.USER);
        newOwner.setPassword(encodedPassword);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = confirmationTokenFactoryInterface.createConfirmationToken(
                token, LocalDateTime.now(), LocalDateTime.now().plusMinutes(15), newOwner);
        ownerRepository.save(newOwner);
        confirmationTokenService.saveConfirmationToken(confirmationToken);

        return token;
    }

    public boolean existsByUsername(Name userName) {

        return ownerRepository.existsByUserName(userName);
    }

    public boolean existsByEmail(Email email) {

        return ownerRepository.existsByEmail(email);
    }

    public Optional<Owner> findById(UUID ownerId) {

        return ownerRepository.findById(ownerId);
    }

    public int enableOwner(String email) {

        return ownerRepository.enableAppUser(email);
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Email emailToFind = Email.createEmail(email);

        return ownerRepository.findByEmail(emailToFind)
                .orElseThrow(() ->
                        new UsernameNotFoundException(String.format(USER_NOT_FOUND, email)));
    }
}
