package com.wftd.kongyan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import com.wftd.kongyan.R;
import com.wftd.kongyan.app.App;
import com.wftd.kongyan.base.BaseActivity;
import com.wftd.kongyan.callback.LoginCallback;
import com.wftd.kongyan.callback.PeopleCallback;
import com.wftd.kongyan.db.DBManager;
import com.wftd.kongyan.entity.People;
import com.wftd.kongyan.util.CommonUtils;
import com.wftd.kongyan.util.HttpUtils;
import com.wftd.kongyan.util.PhoneUtils;
import com.wftd.kongyan.util.StringUtils;
import com.wftd.kongyan.util.ToastUtils;
import com.wftd.kongyan.view.PowerfulEditText;
import java.util.List;
import org.xutils.ex.DbException;

/**
 * 登录页面
 *
 * @author AndSync
 * @date 2018/7/5
 * Copyright © 2014-2018 北京智阅网络科技有限公司 All rights reserved.
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginCallback, PeopleCallback {
    private PowerfulEditText mUserName;
    private PowerfulEditText mPassWord;
    private TextView mLogin;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 2:
                    ToastUtils.show(context, "登录失败,请检查用户名密码是否正确");
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        init();
    }

    private void init() {
        mUserName = (PowerfulEditText) findViewById(R.id.userName);
        mPassWord = (PowerfulEditText) findViewById(R.id.passWord);
        mLogin = (TextView) findViewById(R.id.login);

        mLogin.setOnClickListener(this);
        DBManager.copyDatabaseFile(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.login:
                final String PhoneNumber = mUserName.getText().toString();
                final String pwd = mPassWord.getText().toString();
                if (StringUtils.isEmpty(PhoneNumber) || StringUtils.isEmpty(pwd)) {
                    ToastUtils.show(this, "请输入用户名和密码");
                    return;
                }
                if (PhoneNumber.length() < 11
                    || pwd.length() < 6
                    || !CommonUtils.isMobile(PhoneNumber)
                    || !CommonUtils.isPassWord(pwd)) {
                    ToastUtils.show(this, "用户名或密码错误");
                    return;
                }

                if (PhoneUtils.isNetworkAvailable(this)) {
                    HttpUtils.LoginGet(PhoneNumber, pwd, LoginActivity.this);
                } else {//没有网络的情况下
                    try {
                        People user = db.selector(People.class)
                            .where("phoneNumber", "=", PhoneNumber)
                            .and("passwordText", "=", pwd)
                            .findFirst();
                        if (user == null) {
                            ToastUtils.show(this, "用户名或密码错误");
                            return;
                        }
                        App.loginUser = user;
                        Intent homePagdeIntent = new Intent(LoginActivity.this, HomePageActivity.class);
                        startActivity(homePagdeIntent);
                        finish();
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                }

                break;
            case R.id.home_back:
                finish();
                break;
            default:
                break;
        }
    }

    @Override
    public boolean success(People obj) {
        Intent homePageIntent = new Intent(LoginActivity.this, HomePageActivity.class);
        startActivity(homePageIntent);
        try {
            db.saveOrUpdate(obj);
        } catch (DbException e) {
            e.printStackTrace();
        }
        if (obj.getOrganizationId() != null) {
            HttpUtils.PeopleGet(obj.getOrganizationId(), this);
        }
        finish();
        return false;
    }

    @Override
    public boolean success(List<People> peopleList) {
        try {
            db.saveOrUpdate(peopleList);
            Log.e("aaa", "数据更新成功");
        } catch (DbException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean fail() {
        mHandler.sendEmptyMessage(2);
        Log.e("fail", "登录失败,请检查用户名密码是否正确");
        return false;
    }
}
