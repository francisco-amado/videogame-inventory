package com.inventory.app.domain.valueobjects;

import com.inventory.app.exceptions.BusinessRulesException;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.stereotype.Component;

import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Embeddable
@Component
public class Email {

    private @Getter @Setter String email;

    public Email() {}

    public static String createEmail(String email) {

        if (email == null || email.isEmpty() || email.isBlank()) {
            throw new BusinessRulesException("E-mail cannot be null or empty");
        }

        if (!emailRegexPatternValidation(email)) {
            throw new BusinessRulesException("E-mail does not meet correct format");
        } else {
            return email;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Email email1 = (Email) o;
        return email.equals(email1.email);
    }

    @Override
    public int hashCode() {
        return Objects.hash(email);
    }
}
