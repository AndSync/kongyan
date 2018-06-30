package com.wftd.kongyan.callback;

import com.wftd.kongyan.bean.User;

import java.util.List;

/**
 * 登录回调
 */
public interface PeopleCallback {
    boolean success(List<User> mLoginResult);

    boolean fail();
}
