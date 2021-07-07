package com.example.apnabazaar;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Utils {
    private static final int MILES = 1000;
    public static boolean checkEmailForValidity(String email){

        email = email.trim();

        Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
        return matcher.find();
    }

    public static boolean checkPasswordForValidity(String password){
        password = password.trim();

        Matcher matcher1 = VALIID_PASSWORD_REGEX.matcher(password);
        return  matcher1.find();

    }

    private static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    private static final Pattern VALIID_PASSWORD_REGEX =
            Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{4,}$", Pattern.CASE_INSENSITIVE);
}
