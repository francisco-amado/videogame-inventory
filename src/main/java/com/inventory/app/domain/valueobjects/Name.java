package com.inventory.app.domain.valueobjects;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Component
public class Name {

    private @Getter @Setter String name;

    public Name(String name) {
        this.name = name;
    }

    public Name() {}

    public static Name createName(String name) {

        if (Objects.equals(name, "")) {
            throw new IllegalArgumentException("Name field must not be empty");
        }

        if (name == null) {
            throw new NullPointerException("Null value cannot be passed");
        }

        return new Name(name.trim());
    }
}
