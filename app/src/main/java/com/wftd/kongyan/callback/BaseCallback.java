package com.wftd.kongyan.callback;

/**
 * 接口基类
 */
public interface BaseCallback {
    boolean success(int callBackId,Object obj);
    boolean fail();
}
