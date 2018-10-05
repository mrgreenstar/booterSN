package com.mrgreenstar.sn.validators;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class EmailValidator {
    public boolean isValidEmail(String email) {
        try {
            InternetAddress newEmail = new InternetAddress(email);
            newEmail.validate();
        }
        catch (AddressException exc) {
            return false;
        }
        return true;
    }
}
