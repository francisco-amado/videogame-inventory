package com.inventory.app.domain.factories;

import com.inventory.app.domain.owner.Owner;
import com.inventory.app.domain.token.ConfirmationToken;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class ConfirmationTokenFactory implements ConfirmationTokenFactoryInterface {

    public ConfirmationToken createConfirmationToken(
            String token, LocalDateTime created, LocalDateTime expires, Owner owner) {

       return new ConfirmationToken(token, created, expires, owner);
    }
}
