package com.example.membersmanagement.helpers;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validator {
    public static boolean isPhoneNumber(String phone) {
        String regexPattern = "(84|0[3|5|7|8|9])+([0-9]{8})\\b";
        return phone.matches(regexPattern);
    }

    public static boolean isEmail(String emailAddress) {

        Pattern regexPattern = Pattern.compile("^[(a-zA-Z-0-9-\\_\\+\\.)]+@[(a-z-A-z)]+\\.[(a-zA-z)]{2,3}$");
        Matcher regMatcher = regexPattern.matcher(emailAddress);
        return regMatcher.matches();
    }
}
