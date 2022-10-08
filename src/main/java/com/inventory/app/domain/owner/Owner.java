package com.inventory.app.domain.owner;

import com.inventory.app.domain.valueobjects.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "owner")
public class Owner implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    UUID ownerId;
    @Embedded
    Name userName;
    @Embedded
    Email email;
    @Embedded
    Password password;

    private static final long serialVersionUID = 3L;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Owner owner = (Owner) o;
        return Objects.equals(ownerId, owner.ownerId) &&
                Objects.equals(userName, owner.userName) &&
                Objects.equals(email, owner.email) &&
                Objects.equals(password, owner.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ownerId, userName, email, password);
    }
}
