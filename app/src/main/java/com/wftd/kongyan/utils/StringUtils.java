package com.wftd.kongyan.utils;

/**
 * 字符串判断
 */
public class StringUtils {
    public static boolean isEmpty(String str) {
        if (null == str || "".equals(str) || str.length() == 0) {
            return true;
        }
        return false;
    }
}
