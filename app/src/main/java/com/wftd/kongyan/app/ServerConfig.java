package com.wftd.kongyan.app;

import com.wftd.kongyan.BuildConfig;

/**
 * @author AndSync
 * @date 2017/11/13
 * Copyright © 2014-2017 北京智阅网络科技有限公司 All rights reserved.
 */
public class ServerConfig {

    /**
     * true 为debug状态，打印日志;false为上线发布状态
     */
    public static boolean DEBUG = BuildConfig.DEBUG;

    //public static String getServerUrl(String func) {
        //StringBuilder serverBuilder = new StringBuilder("");
        //serverBuilder.append(ConfigHelper.getSysConfig().getServerQcttUrl());
        //serverBuilder.append("/");
        //serverBuilder.append(func);
        //return serverBuilder.toString();
    //}
}
