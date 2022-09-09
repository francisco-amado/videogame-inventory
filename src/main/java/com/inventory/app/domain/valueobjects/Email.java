package com.inventory.app.domain.valueobjects;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Email {

    private String email;

    public Email(String email) {

        this.email = email;
    }

    public static Email createEmail(String email) {

        if (email == null || email.isEmpty() || email.isBlank()) {

            throw new IllegalArgumentException("E-mail cannot be null or empty");
        }

        if (!emailRegexPatternValidation(email)) {

            throw new IllegalArgumentException("E-mail does not meet correct format");

        } else {

            return new Email(email);
        }
    }

    public static boolean emailRegexPatternValidation(String email) {

        boolean isValid;
        String regex = "^(?=.{1,64}@)[A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*@"
                + "[^-][A-Za-z0-9\\+-]+(\\.[A-Za-z0-9\\+-]+)*(\\.[A-Za-z]{2," +
                "})$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        isValid = matcher.matches();

        return isValid;
    }
}
