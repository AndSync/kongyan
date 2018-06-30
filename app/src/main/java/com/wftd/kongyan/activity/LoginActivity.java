package com.wftd.kongyan.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.wftd.kongyan.R;
import com.wftd.kongyan.base.BaseActivity;
import com.wftd.kongyan.bean.User;
import com.wftd.kongyan.callback.LoginCallback;
import com.wftd.kongyan.callback.PeopleCallback;
import com.wftd.kongyan.db.DBManager;
import com.wftd.kongyan.utils.CommonUtils;
import com.wftd.kongyan.utils.HttpUtils;
import com.wftd.kongyan.utils.PhoneUtils;
import com.wftd.kongyan.utils.StringUtils;
import com.wftd.kongyan.utils.ToastUtils;
import com.wftd.kongyan.view.PowerfulEditText;
import java.util.List;
import org.xutils.ex.DbException;

/**
 * 登录页
 */
public class LoginActivity extends BaseActivity implements View.OnClickListener, LoginCallback, PeopleCallback {
    private PowerfulEditText mUserName;
    private PowerfulEditText mPassWord;
    private TextView mLogin;
    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case 1:

                    break;
                case 2:
                    Toast.makeText(LoginActivity.this,"登录失败,请检查用户名密码是否正确",Toast.LENGTH_SHORT).show();
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
        //mHelper.addUser("15011050191","aaaa","15011050191","123456");
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
                if (PhoneNumber.length() < 11 || pwd.length() < 6 || !CommonUtils.isMobile(PhoneNumber) || !CommonUtils.isPassWord(pwd)) {
                    ToastUtils.show(this, "用户名或密码错误");
                    return;
                }

//检测网络是否连接

                if (PhoneUtils.isNetworkAvailable(this)) {
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            //Intent intent=new Intent(LoginActivity.this,SelectActivity.class);
                            //startActivity(intent);

                            //HttpUtils.LoginPost(userName, pwd, (LoginCallback) LoginActivity.this);
                        }
                    }).start();
                    HttpUtils.LoginGet(PhoneNumber, pwd, (LoginCallback) LoginActivity.this);
                } else {//没有网络的情况下
                    try {
                        User user = db.selector(User.class).where("phoneNumber", "=", PhoneNumber).and("passwordText", "=", pwd).findFirst();
                        if (user == null) {
                            ToastUtils.show(this, "用户名或密码错误");
                            return;
                        }

                        Intent homePagdeIntent = new Intent(LoginActivity.this, HomePageActivity.class);
                        homePagdeIntent.putExtra("user", user);
                        startActivity(homePagdeIntent);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }

                }

                break;
            case R.id.home_back:
                finish();
                break;
        }
    }


    @Override
    public boolean success(User obj) {
        try {
            obj.setPhoneNumber(mUserName.getText().toString());
            db.save(obj);
        } catch (DbException e) {
            e.printStackTrace();
        }
        Intent homePageIntent = new Intent(LoginActivity.this, HomePageActivity.class);
        homePageIntent.putExtra("user", obj);
        startActivity(homePageIntent);

        if (obj.getOrganizationId() != null) {
            HttpUtils.PeopleGet(obj.getOrganizationId(), this);
        }
        return false;
    }

    @Override
    public boolean success(List<User> mLoginResult) {
        try {
            db.save(mLoginResult);
            Log.e("aaa","数据更新成功");
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
