package com.wftd.kongyan.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import com.wftd.kongyan.R;
import com.wftd.kongyan.app.UserHelper;
import com.wftd.kongyan.base.BaseActivity;
import com.wftd.kongyan.callback.ModifyCallback;
import com.wftd.kongyan.entity.People;
import com.wftd.kongyan.util.CommonUtils;
import com.wftd.kongyan.util.HttpUtils;
import com.wftd.kongyan.util.PhoneUtils;
import com.wftd.kongyan.util.ToastUtils;
import org.xutils.ex.DbException;

/**
 * @author AndSync
 * @date 2018/7/2
 * Copyright © 2014-2018 AndSync All rights reserved.
 */
public class ModifyPasswordActivity extends BaseActivity implements View.OnClickListener, ModifyCallback {

    private EditText old_password;
    private EditText new_password;
    private EditText check_password;
    private TextView confirm;
    private People user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modify);
        old_password = (EditText) findViewById(R.id.old_password);
        new_password = (EditText) findViewById(R.id.new_password);
        check_password = (EditText) findViewById(R.id.check_password);
        confirm = (TextView) findViewById(R.id.confirm);
        ImageView back = (ImageView) findViewById(R.id.back);
        back.setOnClickListener(this);
        confirm.setOnClickListener(this);
        user = UserHelper.getUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.back:
                onBackPressed();
                break;
            case R.id.confirm:
                if (CommonUtils.isPassWord(old_password.getText().toString())) {
                    if (CommonUtils.isPassWord(new_password.getText().toString())) {
                        if (new_password.getText().toString().equals(check_password.getText().toString())) {
                            if (PhoneUtils.isNetworkAvailable(context)) {
                                HttpUtils.modifyGet(user.getPhoneNumber(), check_password.getText().toString(), this);
                            } else {
                                ToastUtils.show(context, "请检查网络状况");
                            }
                        } else {
                            ToastUtils.show(context, "修改两次密码应该相同");
                        }
                    } else {
                        ToastUtils.show(context, "新密码应大于6位");
                    }
                } else {
                    ToastUtils.show(context, "请输入正确的原始密码");
                }
                break;
            default:
                break;
        }
    }

    @Override
    public void success() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show(context,"修改密码成功");
                user.setPasswordText(check_password.getText().toString());
                try {
                    db.update(user, "passwordText");
                } catch (DbException e) {
                    e.printStackTrace();
                }
                finish();
            }
        });
    }

    @Override
    public void fail() {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                ToastUtils.show(context, "修改密码失败请确认原密码");
            }
        });
    }
}
