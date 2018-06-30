package com.wftd.kongyan.app;

import com.wftd.kongyan.entity.Doctor;
import com.wftd.kongyan.entity.LoginResult;
import com.wftd.kongyan.entity.Ser1UserInfo;
import com.wftd.kongyan.util.FileHelper;
import com.wftd.kongyan.util.LogUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户信息管理类
 *
 * @author AndSync
 * @date 2016/5/18
 * Copyright © 2014-2017 北京智阅网络科技有限公司 All rights reserved.
 */
public class UserHelper {
    /**
     * 用户信息--本地保存的序列化对象文件名
     */
    public static final String KEY_USER_INFO = "userinfo.obj";

    /**
     * 获取用户ID
     */
    //public static String getUserId() {
    //Ser1UserInfo userInfo = getUserInfo();
    //String userId;
    //if (TextUtils.isEmpty(userInfo.getUid())) {
    //    //临时ID
    //    userId = AppSpHelper.getTempUserId();
    //} else {
    //    userId = userInfo.getUid();
    //}
    //return userId;
    //}

    /**
     * 设置用户信息
     */
    public static void setUserInfo(LoginResult userInfo) {
        if (userInfo != null) {
            FileHelper.saveObject2File(userInfo, KEY_USER_INFO);
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
    public static Ser1UserInfo getUserInfo() {
        LoginResult loginResult = (LoginResult) FileHelper.readObjectFromFile(KEY_USER_INFO);
        if (loginResult != null) {
            Ser1UserInfo urlInfo = loginResult.getPeople();
            if (urlInfo != null) {
                LogUtils.d("UserInfo", urlInfo.toString());
                return urlInfo;
            } else {
                return new Ser1UserInfo();
            }
        } else {
            return new Ser1UserInfo();
        }
    }

    /**
     * 用户是否登录
     */
    public static boolean isLogin() {
        Ser1UserInfo userInfo = getUserInfo();
        return userInfo.isLogin();
    }
    //
    //public static String getNikeName() {
    //    Ser1UserInfo userInfo = getUserInfo();
    //    return userInfo.getNickname();
    //}

    /**
     * 获取用户头像
     */
    //public static String getHeadLogo() {
    //    Ser1UserInfo userInfo = getUserInfo();
    //    return userInfo.getAvatar();
    //}

    /***
     * 清空用户信息
     */
    public static void clearUserInfo() {
        FileHelper.saveObject2File(new LoginResult(), KEY_USER_INFO);
    }
}
