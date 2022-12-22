package com.inventory.app.domain.owner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collections;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Owner implements Serializable, UserDetails {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    private final UUID ownerId = UUID.randomUUID();
    @Embedded
    private Name userName;
    @Embedded
    private Email email;
    @JsonIgnore
    private String password;
    @OneToOne(mappedBy = "owner")
    @JsonIgnore
    private Collection collection;
    @Enumerated(EnumType.STRING)
    private OwnerRole ownerRole;
    private boolean locked = false;
    private boolean enabled = false;

    private static final long serialVersionUID = 3L;

    public Owner(Name userName, Email email, String password, Collection collection,
                 OwnerRole ownerRole, boolean locked, boolean enabled) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.collection = collection;
        this.ownerRole = ownerRole;
        this.locked = locked;
        this.enabled = enabled;
    }

    public Owner(Name userName, Email email, String password) {

        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Owner() {}

    public UUID getOwnerId() {
        return ownerId;
    }

    public Email getEmail() {
        return email;
    }

    public Name getUserName() {
        return userName;
    }

    public OwnerRole getOwnerRole() {
        return ownerRole;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setOwnerRole(OwnerRole ownerRole) {
        this.ownerRole = ownerRole;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public java.util.Collection<? extends GrantedAuthority> getAuthorities() {
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority(ownerRole.name());
        return Collections.singletonList(authority);
    }

    @Override
    public String getPassword() {
        return password.toString();
    }

    @Override
    public String getUsername() {
        return userName.getName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return enabled;
    }

    public Collection getCollection() {
        return collection;
    }

    public void setCollection(Collection collection) {
        this.collection = collection;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(ownerId, owner.ownerId) &&
                Objects.equals(userName, owner.userName) &&
                Objects.equals(email, owner.email) &&
                Objects.equals(password, owner.password) &&
                Objects.equals(collection, owner.collection);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, userName, email, password);
    }
}
