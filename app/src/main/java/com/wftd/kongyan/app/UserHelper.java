package com.wftd.kongyan.app;

import com.wftd.kongyan.entity.Doctor;
import com.wftd.kongyan.entity.LoginResult;
import com.wftd.kongyan.entity.People;
import com.wftd.kongyan.util.FileHelper;
import com.wftd.kongyan.util.LogUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息管理类
 *
 * @author AndSync
 * @date 2016/5/18
 * Copyright © 2014-2017 AndSync All rights reserved.
 */
public class UserHelper {
    /**
     * 用户信息--本地保存的序列化对象文件名
     */
    public static final String KEY_USER_INFO = "userinfo.obj";

    /**
     * 设置用户信息
     */
    public static void setUserInfo(LoginResult userInfo) {
        if (userInfo != null) {
            App.loginUser = userInfo.getPeople();
            FileHelper.saveObject2File(userInfo, KEY_USER_INFO);
        }
    }

    public static void updateDoctorList(List<Doctor> doctorList) {
        LoginResult loginResult = (LoginResult) FileHelper.readObjectFromFile(KEY_USER_INFO);
        //LoginResult loginResult = App.loginResult;
        if (loginResult != null) {
            loginResult.setDoctors(doctorList);
            setUserInfo(loginResult);
        }
    }

    public static List<Doctor> getDoctorList() {
        LoginResult loginResult = (LoginResult) FileHelper.readObjectFromFile(KEY_USER_INFO);
        //LoginResult loginResult = App.loginResult;
        List<Doctor> doctorList;
        if (loginResult != null) {
            doctorList = loginResult.getDoctors();
        } else {
            doctorList = new ArrayList<>();
        }
        return doctorList;
    }

    /**
     * 获取用户信息
     */
    public static People getUserInfo() {
        People urlInfo = App.loginUser;
        if (urlInfo != null) {
            LogUtils.d("UserInfo", urlInfo.toString());
            return urlInfo;
        } else {
            return new People();
        }
    }

    /**
     * 用户是否登录
     */
    public static boolean isLogin() {
        People userInfo = getUserInfo();
        return userInfo.isLogin();
    }

    /***
     * 清空用户信息
     */
    public static void clearUserInfo() {
        App.loginUser = null;
        //FileHelper.saveObject2File(new LoginResult(), KEY_USER_INFO);
    }
}
