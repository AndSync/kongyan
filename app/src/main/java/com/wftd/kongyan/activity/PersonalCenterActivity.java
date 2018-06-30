package com.wftd.kongyan.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.wftd.kongyan.R;
import com.wftd.kongyan.app.App;
import com.wftd.kongyan.app.UserHelper;
import com.wftd.kongyan.base.BaseActivity;
import com.wftd.kongyan.callback.VersionCallback;
import com.wftd.kongyan.entity.Ser1UserInfo;
import com.wftd.kongyan.entity.Version;
import com.wftd.kongyan.util.AppUtils;
import com.wftd.kongyan.util.HttpUtils;

public class PersonalCenterActivity extends BaseActivity implements View.OnClickListener {
    private ImageView mBack;
    private TextView mExit;
    private TextView mRealName;
    private TextView mUserName;
    private ImageView todata;
    private ImageView message;
    private Ser1UserInfo user = UserHelper.getUserInfo();

    private TextView people_name, people_job, hosp_name, equipment;
    private ImageView iv_new_version;
    private TextView tv_version;

    private Version versionInfo;
    private LinearLayout layout_modify;
    private LinearLayout layout_version;
    private LinearLayout layout_message;
    private ImageView iv_arrow_version;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_center);
        init();
    }

    private void init() {
        mBack = (ImageView) findViewById(R.id.personal_back);
        mExit = (TextView) findViewById(R.id.personal_exit);
        mRealName = (TextView) findViewById(R.id.personal_real_name);
        todata = (ImageView) findViewById(R.id.modify);
        message = (ImageView) findViewById(R.id.message);

        people_name = (TextView) findViewById(R.id.people_name);
        people_job = (TextView) findViewById(R.id.people_job);
        hosp_name = (TextView) findViewById(R.id.hosp_name);
        equipment = (TextView) findViewById(R.id.equipment);

        iv_new_version = (ImageView) findViewById(R.id.iv_new_version);
        tv_version = (TextView) findViewById(R.id.tv_version);
        layout_modify = (LinearLayout) findViewById(R.id.layout_modify);
        layout_version = (LinearLayout) findViewById(R.id.layout_version);
        layout_message = (LinearLayout) findViewById(R.id.layout_message);
        iv_arrow_version = (ImageView) findViewById(R.id.iv_arrow_version);

        mBack.setOnClickListener(this);
        mExit.setOnClickListener(this);
        todata.setOnClickListener(this);
        message.setOnClickListener(this);
        layout_modify.setOnClickListener(this);
        layout_version.setOnClickListener(this);
        layout_message.setOnClickListener(this);
        people_name.setText(user.getName());
        people_job.setText(user.getPost());
        hosp_name.setText(user.getOrgnizationName());
        equipment.setText(user.getPhoneNumber());
        tv_version.setText("版本" + AppUtils.getVersionName(this));
        HttpUtils.appUpdate(new VersionCallback() {
            @Override
            public boolean success(final Version version) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        versionInfo = version;
                        if (version.hasNewVersion(PersonalCenterActivity.this)) {
                            versionInfo = version;
                            iv_new_version.setVisibility(View.VISIBLE);
                            iv_arrow_version.setVisibility(View.VISIBLE);
                        } else {
                            iv_new_version.setVisibility(View.GONE);
                            iv_arrow_version.setVisibility(View.GONE);
                        }
                    }
                });

                return false;
            }

            @Override
            public boolean fail() {
                return false;
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.personal_back:
                //返回
                finish();
                break;
            case R.id.personal_exit:
                //退出
                UserHelper.clearUserInfo();
                App.getInstance().exit();
                break;
            case R.id.modify:
            case R.id.layout_modify:
                Intent intent = new Intent(PersonalCenterActivity.this, ModifyPwdActivity.class);
                startActivity(intent);
                break;
            case R.id.message:
            case R.id.layout_message:
                Intent msgintent = new Intent(PersonalCenterActivity.this, MsgActivity.class);
                startActivity(msgintent);
                break;
            case R.id.layout_version:
                if (versionInfo != null && versionInfo.hasNewVersion(PersonalCenterActivity.this)) {
                    Uri uri = Uri.parse(versionInfo.getUrl());
                    Intent intent2 = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent2);
                }
                break;
            default:
                break;
        }
    }
}
