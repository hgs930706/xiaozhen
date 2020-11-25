package com.yishang.xiaozhen.util;


import java.time.format.DateTimeFormatter;
import java.util.regex.Pattern;

public class DateUtil {
    public static final DateTimeFormatter dateFormatter1 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
    public static final DateTimeFormatter dateFormatter2 = DateTimeFormatter.ofPattern("yyyy/MM/dd");
    public static final DateTimeFormatter dateFormatter3 = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    public static final DateTimeFormatter dateFormatter4 = DateTimeFormatter.ofPattern("yyyyMMdd");
    public static final DateTimeFormatter dateFormatter5 = DateTimeFormatter.ofPattern("yyyy年MM月dd日 HH:mm");
    public static final DateTimeFormatter dateFormatter6 = DateTimeFormatter.ofPattern("MM月dd日");


    /**
     *  正则：手机号（简单）, 1字头＋10位数字即可.
     * @param in
     * @return
     */
    public static boolean validateMobilePhone(String in) {
        Pattern pattern = Pattern.compile("^[1]\\d{10}$");
        return pattern.matcher(in).matches();
    }
}
