package com.wftd.kongyan.callback;

import com.wftd.kongyan.entity.People;

/**
 * 登录回调
 */
public interface LoginCallback {
    boolean success(People mLoginResult);

    boolean fail();
}
