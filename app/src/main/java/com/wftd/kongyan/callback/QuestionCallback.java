package com.wftd.kongyan.callback;

import com.wftd.kongyan.entity.Question;

/**
 */
public interface QuestionCallback {

    void success();

    void success(Question question);

    void fail();
}
