package com.inventory.app.domain.token;

import com.inventory.app.domain.owner.Owner;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
public class ConfirmationToken {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private final UUID tokenId = UUID.randomUUID();
    private String token;
    private LocalDateTime created;
    private LocalDateTime expires;
    private LocalDateTime confirmed;
    @ManyToOne(fetch = FetchType.LAZY)
    private Owner owner;

    public ConfirmationToken(String token, LocalDateTime created, LocalDateTime expires, Owner owner) {
        this.token = token;
        this.created = created;
        this.expires = expires;
        this.owner = owner;
    }

    public ConfirmationToken() {
    }

    public UUID getTokenId() {
        return tokenId;
    }

    public String getToken() {
        return token;
    }

    public LocalDateTime getCreated() {
        return created;
    }

    public LocalDateTime getExpires() {
        return expires;
    }

    public LocalDateTime getConfirmed() {
        return confirmed;
    }

    public Owner getOwner() {
        return owner;
    }
}
