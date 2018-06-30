package com.wftd.kongyan.callback;

import com.wftd.kongyan.entity.Ser1UserInfo;
import java.util.List;

/**
 * 登录回调
 */
public interface PeopleCallback {
    boolean success(List<Ser1UserInfo> mLoginResult);

    boolean fail();
}
