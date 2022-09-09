package com.inventory.app.domain.valueobjects;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public class Name {

    String name;

    public Name(String name) {

        this.name = name;
    }

    public Name() {

    }

    public Name createName(String name) {

        if (Objects.equals(name, "")) {

            throw new IllegalArgumentException("Name field must not be empty");
        }

        if (name == null) {

            throw new NullPointerException("Null value cannot be passed");
        }

        return new Name(name);
    }
}
