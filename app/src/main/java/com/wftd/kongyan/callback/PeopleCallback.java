package com.wftd.kongyan.callback;

import com.wftd.kongyan.entity.People;
import java.util.List;

/**
 * 登录回调
 */
public interface PeopleCallback {
    boolean success(List<People> mLoginResult);

    boolean fail();
}
