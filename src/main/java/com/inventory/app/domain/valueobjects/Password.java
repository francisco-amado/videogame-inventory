package com.inventory.app.domain.valueobjects;

import org.mindrot.jbcrypt.BCrypt;
import java.util.Objects;

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
}
