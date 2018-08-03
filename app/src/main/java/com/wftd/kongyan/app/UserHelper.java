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
    public static final String KEY_USER_INFO_PEOPLE = "userinfo_people.obj";

    /**
     * 设置用户信息
     */
    public static void setUserInfo(LoginResult userInfo) {
        if (userInfo != null) {
            FileHelper.saveObject2File(userInfo, KEY_USER_INFO);
            FileHelper.saveObject2File(userInfo.getPeople(), KEY_USER_INFO_PEOPLE);
        }
    }

    public static void setUserInfoPeople(People people) {
        if (people != null) {
            FileHelper.saveObject2File(people, KEY_USER_INFO_PEOPLE);
        }
    }

    public static void updateDoctorList(List<Doctor> doctorList) {
        LoginResult loginResult = (LoginResult) FileHelper.readObjectFromFile(KEY_USER_INFO);
        if (loginResult != null) {
            loginResult.setDoctors(doctorList);
            setUserInfo(loginResult);
        }
    }

    public static List<Doctor> getDoctorList() {
        LoginResult loginResult = (LoginResult) FileHelper.readObjectFromFile(KEY_USER_INFO);
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
        People userInfo = (People) FileHelper.readObjectFromFile(KEY_USER_INFO_PEOPLE);
        if (userInfo != null) {
            LogUtils.d("UserInfo", userInfo.toString());
            return userInfo;
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
        FileHelper.saveObject2File(new People(), KEY_USER_INFO_PEOPLE);
    }
}
