package com.wftd.kongyan.callback;

import com.wftd.kongyan.bean.Message;

import java.util.List;

/**
 */
public interface MessageCallback {
    boolean success(List<Message> messages);

    boolean fail();
}
