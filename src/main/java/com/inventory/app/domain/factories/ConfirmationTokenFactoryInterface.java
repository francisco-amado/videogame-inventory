package com.inventory.app.domain.factories;

import com.inventory.app.domain.confirmationtoken.ConfirmationToken;
import com.inventory.app.domain.owner.Owner;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public interface ConfirmationTokenFactoryInterface {

    public ConfirmationToken createConfirmationToken(
            String token, LocalDateTime created, LocalDateTime expires, Owner owner);
}
