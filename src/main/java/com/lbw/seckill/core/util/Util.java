package com.lbw.seckill.core.util;

import java.util.regex.Pattern;

public class Util {
    public static boolean checkPassword(String pwd) {
        if (pwd == null) {
            return false;
        }
        String regex = "^.{8,18}$";
        return Pattern.matches(regex, pwd);
    }
}
