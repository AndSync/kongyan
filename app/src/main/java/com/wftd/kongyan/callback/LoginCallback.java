package com.wftd.kongyan.callback;

import com.wftd.kongyan.entity.Ser1UserInfo;

/**
 * 登录回调
 */
public interface LoginCallback {
    boolean success(Ser1UserInfo mLoginResult);

    boolean fail();
}
