package com.inventory.app.domain.owner;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.inventory.app.domain.collection.Collection;
import com.inventory.app.domain.valueobjects.Email;
import com.inventory.app.domain.valueobjects.Name;
import com.inventory.app.domain.valueobjects.Password;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "owner")
public class Owner implements Serializable {

    @Id
    @Type(type = "org.hibernate.type.UUIDCharType")
    UUID ownerId = UUID.randomUUID();
    @Embedded
    Name userName;
    @Embedded
    Email email;
    @Embedded
    Password password;
    @OneToOne(mappedBy = "owner")
    @JsonIgnore
    Collection collection;

    private static final long serialVersionUID = 3L;

    public Owner(Name userName, Email email, Password password, Collection collection) {
        this.userName = userName;
        this.email = email;
        this.password = password;
        this.collection = collection;
    }

    public Owner(Name userName, Email email, Password password) {

        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Owner() {

    }

    public UUID getOwnerId() {
        return ownerId;
    }

    public Name getUserName() {
        return userName;
    }

    public Email getEmail() {
        return email;
    }

    public Password getPassword() {
        return password;
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
