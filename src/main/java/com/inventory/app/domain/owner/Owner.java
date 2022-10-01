package com.inventory.app.domain.owner;

import com.inventory.app.domain.valueobjects.*;
import javax.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Entity
@Table(name = "owner")
public class Owner implements Serializable {

    @EmbeddedId
    OwnerId ownerId;
    @Embedded
    Name userName;
    @Embedded
    Email email;
    @Embedded
    Password password;

    private static final long serialVersionUID = 3L;

    public Owner(OwnerId ownerId, Name userName, Email email, Password password) {

        this.ownerId = ownerId;
        this.userName = userName;
        this.email = email;
        this.password = password;
    }

    public Owner() {

    }

    public OwnerId getOwnerId() {
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
