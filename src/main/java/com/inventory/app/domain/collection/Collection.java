package com.inventory.app.domain.collection;

import com.inventory.app.domain.game.Game;
import com.inventory.app.domain.owner.Owner;
import com.sun.istack.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.hateoas.RepresentationModel;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
public class Collection {

    @Id
    @Column(name = "uuid")
    @Type(type = "org.hibernate.type.UUIDCharType")
    private final @Getter UUID collectionId = UUID.randomUUID();

    @OneToOne(optional = false)
    @JoinColumn(name = "owner", referencedColumnName = "uuid", nullable = false)
    private @Getter @Setter Owner owner;

    @OneToMany(mappedBy = "collection")
    @NotNull
    private @Getter @Setter List<Game> gameList;

    public Collection(Owner owner, List<Game> gameList) {
        this.owner = owner;
        this.gameList = gameList;
    }

    public Collection() {}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Collection that = (Collection) o;
        return Objects.equals(collectionId, that.collectionId) &&
                Objects.equals(owner, that.owner) &&
                Objects.equals(gameList, that.gameList);
    }

    @Override
    public int hashCode() {
        return Objects.hash(collectionId, owner, gameList);
    }
}
