package com.wftd.kongyan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import com.wftd.kongyan.R;
import com.wftd.kongyan.adapter.MessageAdapter;
import com.wftd.kongyan.app.UserHelper;
import com.wftd.kongyan.base.BaseActivity;
import com.wftd.kongyan.callback.MessageCallback;
import com.wftd.kongyan.entity.Message;
import com.wftd.kongyan.entity.People;
import com.wftd.kongyan.util.HttpUtils;
import com.wftd.kongyan.util.ToastUtils;
import java.util.ArrayList;
import java.util.List;

/**
 * @author AndSync
 * @date 2018/7/1
 * Copyright © 2014-2018 AndSync All rights reserved.
 */
public class MessageActivity extends BaseActivity implements MessageCallback {
    private List<Message> dataList = new ArrayList<>();
    private MessageAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        People user = UserHelper.getUserInfo();
        HttpUtils.MessageGet(user.getId(), this);
        ListView mListView = (ListView) findViewById(R.id.mylistview);
        mAdapter = new MessageAdapter(this, dataList);
        mListView.setAdapter(mAdapter);
        findViewById(R.id.back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    public boolean success(final List<Message> messages) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                if (messages != null) {
                    dataList.clear();
                    dataList.addAll(messages);
                    mAdapter.notifyDataSetChanged();
                } else {
                    ToastUtils.show(context, "暂无数据");
                }
            }
        });
        return false;
    }

    @Override
    public boolean fail() {
        return false;
    }
}
