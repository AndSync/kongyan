package com.wftd.kongyan.callback;

import com.wftd.kongyan.bean.User;

/**
 * 登录回调
 */
public interface LoginCallback {
    boolean success(User mLoginResult);

    boolean fail();
}
