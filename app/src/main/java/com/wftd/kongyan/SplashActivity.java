package com.wftd.kongyan;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import com.wftd.kongyan.activity.HomePageActivity;
import com.wftd.kongyan.activity.LoginActivity;
import com.wftd.kongyan.app.UserHelper;
import com.wftd.kongyan.base.BaseActivity;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 启动页面
 *
 * @author AndSync
 * @date 2018/6/30
 * Copyright © 2014-2018 AndSync All rights reserved.
 */
public class SplashActivity extends BaseActivity {
    private TimerTask mTask;
    private Timer mTimer;
    private static final int MSG_TIME_END = 0x100;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            switch (msg.what) {
                case MSG_TIME_END:
                    startLogin();
                    break;
            }
            super.handleMessage(msg);
        }
    };

    private void startLogin() {
        if (UserHelper.isLogin()) {
            Intent intent = new Intent(this, HomePageActivity.class);
            startActivity(intent);
        } else {
            Intent login = new Intent(this, LoginActivity.class);
            startActivity(login);
        }

        finish();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        setContentView(R.layout.activity_splash);
        startCountDown();
    }

    private void startCountDown() {
        mTask = new TimerTask() {
            @Override
            public void run() {
                mHandler.sendEmptyMessage(MSG_TIME_END);
            }
        };
        mTimer = new Timer();
        mTimer.schedule(mTask, 1500);
    }

    @Override
    protected void onDestroy() {
        if (mTask != null) {
            mTask.cancel();
            mTask = null;
        }
        if (mTimer != null) {
            mTimer.cancel();
            mTimer = null;
        }
        mHandler = null;
        super.onDestroy();
    }
}
