package com.inventory.app.domain.valueobjects;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import java.util.Objects;

@Embeddable
@Component
public class Password {

    private String password;

    private Password(String password) {

        this.password = password;
    }

    public Password() {

    }

    public static Password createPassword(String writtenPassword) {

        if (Objects.isNull(writtenPassword) || writtenPassword.isEmpty() ||
                writtenPassword.isBlank()) {

            throw new IllegalArgumentException("Invalid password");
        }

        return new Password(BCrypt.hashpw(writtenPassword, BCrypt.gensalt(10)));
    }

    public boolean checkIfPasswordHashIsSecure(String writtenPassword) {

        boolean result;

        if (writtenPassword == null) {

            throw new IllegalArgumentException("Invalid password");

        } else {

            result = BCrypt.checkpw(writtenPassword, this.password);
        }

        return result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Password password1 = (Password) o;
        return Objects.equals(password, password1.password);
    }

    @Override
    public int hashCode() {
        return Objects.hash(password);
    }
}
