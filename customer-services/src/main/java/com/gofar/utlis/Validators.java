package com.gofar.utlis;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Validators {

    public static boolean validatePhoneNumber(String phone) {
        Pattern pattern = Pattern.compile("[+|0{2}]228\\d{8}");
        Matcher matcher = pattern.matcher(phone);
        return matcher.matches();
    }
}
