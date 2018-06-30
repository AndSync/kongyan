package com.wftd.kongyan.callback;

import com.wftd.kongyan.entity.User;

/**
 * 登录回调
 */
public interface LoginCallback {
    boolean success(User mLoginResult);

    boolean fail();
}
