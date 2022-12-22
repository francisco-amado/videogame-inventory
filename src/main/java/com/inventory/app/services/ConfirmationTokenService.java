package com.inventory.app.services;

import com.inventory.app.domain.token.ConfirmationToken;
import com.inventory.app.repositories.ConfirmationTokenRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;
    private final OwnerService ownerService;

    public ConfirmationTokenService(ConfirmationTokenRepository confirmationTokenRepository,
                                    OwnerService ownerService) {
        this.confirmationTokenRepository = confirmationTokenRepository;
        this.ownerService = ownerService;
    }

    public void saveConfirmationToken(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.save(confirmationToken);
    }

    public Optional<ConfirmationToken> getToken(String token) {
        return confirmationTokenRepository.findByToken(token);
    }

    public int setConfirmed(String token) {
        return confirmationTokenRepository.updateConfirmed(token, LocalDateTime.now());
    }

    @Transactional
    public String confirmToken(String token) {
        ConfirmationToken confirmationToken =
                getToken(token)
                .orElseThrow(() ->
                        new IllegalStateException("Token not found"));

        if (confirmationToken.getConfirmed() != null) {
            throw new IllegalStateException("E-mail already confirmed");
        }

        LocalDateTime expired = confirmationToken.getExpires();

        if (expired.isBefore(LocalDateTime.now())) {
            throw new IllegalStateException("Token expired");
        }

        setConfirmed(token);
        ownerService.enableOwner(confirmationToken.getOwner().getEmail().getEmail());

        return "Confirmed";
    }
}
