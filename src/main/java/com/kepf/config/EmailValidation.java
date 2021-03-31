package com.kepf.config;

import java.util.regex.Pattern;

public class EmailValidation {
    public static boolean isEmailValid(String email){
        String regrexPattern = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$";
        Pattern pattern = Pattern.compile(regrexPattern);
        return pattern.matcher(regrexPattern).matches();
    }
}
