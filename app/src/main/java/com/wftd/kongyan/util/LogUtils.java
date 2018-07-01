package com.wftd.kongyan.util;

import android.util.Log;
import com.wftd.kongyan.app.ServerConfig;

/**
 * 日志打印工具类
 *
 * @author AndSync
 * @date 2017/10/18
 * Copyright © 2014-2017 AndSync All rights reserved.
 */
public class LogUtils {

    public static void i(String tag, String content) {
        if (ServerConfig.DEBUG) {
            String log = getTraceInfo() + "  :  " + content;
            Log.i(tag, log);
        }
    }

    public static void w(String tag, String content) {
        if (ServerConfig.DEBUG) {
            String log = getTraceInfo() + "  :  " + content;
            Log.w(tag, log);
        }
    }

    public static void e(String tag, String content) {
        if (ServerConfig.DEBUG) {
            String log = getTraceInfo() + "  :  " + content;
            Log.e(tag, log);
        }
    }

    public static void d(String tag, String content) {
        if (ServerConfig.DEBUG) {
            String log = getTraceInfo() + "  :  " + content;
            Log.d(tag, log);
        }
    }

    public static void v(String tag, String content) {
        if (ServerConfig.DEBUG) {
            String log = getTraceInfo() + "  :  " + content;
            Log.v(tag, log);
        }
    }

    /**
     * 获取堆栈信息
     */
    private static String getTraceInfo() {
        StringBuffer sb = new StringBuffer();
        StackTraceElement[] stacks = new Throwable().getStackTrace();
        String className = stacks[2].getClassName();
        int index = className.lastIndexOf('.');
        if (index >= 0) {
            className = className.substring(index + 1, className.length());
        }
        String methodName = stacks[2].getMethodName();
        int lineNumber = stacks[2].getLineNumber();
        sb.append(className).append("->").append(methodName).append("()->").append(lineNumber);
        return sb.toString();
    }
}
