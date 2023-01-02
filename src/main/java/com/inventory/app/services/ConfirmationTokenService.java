package com.inventory.app.services;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.confirmationtoken.ConfirmationToken;
import com.inventory.app.repositories.ConfirmationTokenRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Autowired
    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository) {
        this.confirmationTokenRepository = confirmationTokenRepository;
    }

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public void setConfirmed(String token) {
        confirmationTokenRepository.updateConfirmed(token, LocalDateTime.now());
    }

    public Optional<ConfirmationToken> findByOwner(Owner owner) {
       return confirmationTokenRepository.findByOwner(owner);
    }

    public void delete(ConfirmationToken token) {
        confirmationTokenRepository.delete(token);
    }

}
