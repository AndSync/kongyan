package com.wftd.kongyan.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 常用工具
 */
public class CommonUtils {
    /**
     * 正则表达式：验证手机号
     */
    public static final String REGEX_MOBILE = "^[1][3,4,5,7,8][0-9]{9}$";
    /**
     * 正则表达式：验证密码
     */
    public static final String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,15}$";

    public static boolean isMobile(String phone) {
        Pattern p = Pattern.compile(REGEX_MOBILE);
        Matcher m = p.matcher(phone);
        return m.matches();
    }
    public static boolean isPassWord(String pwd) {
        Pattern p = Pattern.compile(REGEX_PASSWORD);
        Matcher m = p.matcher(pwd);
        return m.matches();
    }
}
