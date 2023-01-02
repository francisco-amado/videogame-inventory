package com.inventory.app.domain.valueobjects;

import com.inventory.app.exceptions.BusinessRulesException;
import lombok.Getter;
import lombok.Setter;
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

        if (name == null || Objects.equals(name, "")) {
            throw new BusinessRulesException("Name field must not be empty");
        }

        return new Name(name.trim());
    }
}
