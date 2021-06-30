package com.lbw.seckill.core.util;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

public class Util {
    public static boolean checkPassword(String pwd) {
        if (pwd == null) {
            return false;
        }
        String regex = "^.{8,18}$";
        return Pattern.matches(regex, pwd);
    }

    public static boolean openUrl(String start, String end) {
        try {
            Date startDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(start);
            Date endDate = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(end);
            Date now = new Date();
            return now.after(startDate) && now.before(endDate);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
