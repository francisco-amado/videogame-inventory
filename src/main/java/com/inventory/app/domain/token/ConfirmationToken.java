package com.inventory.app.domain.token;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.owner.Owner;
import com.sun.istack.NotNull;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
public class ConfirmationToken extends RepresentationModel<ConfirmationToken> implements Serializable {

    @Id
    @Column(name = "uuid")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private final UUID tokenId = UUID.randomUUID();
    @NotNull
    private String token;
    @NotNull
    private LocalDateTime created;
    @NotNull
    private LocalDateTime expires;
    private LocalDateTime confirmed;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner", referencedColumnName = "uuid", nullable = false)
    @JsonIgnore
    private Owner owner;

    public ConfirmationToken(String token, LocalDateTime created, LocalDateTime expires, Owner owner) {
        this.token = token;
        this.created = created;
        this.expires = expires;
        this.owner = owner;
    }

    public ConfirmationToken() {}

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfirmationToken that = (ConfirmationToken) o;
        return Objects.equals(tokenId, that.tokenId) &&
                Objects.equals(token, that.token) &&
                Objects.equals(created, that.created) &&
                Objects.equals(expires, that.expires) &&
                Objects.equals(confirmed, that.confirmed) &&
                Objects.equals(owner, that.owner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(tokenId, token, created, expires, confirmed, owner);
    }
}
