package com.wftd.kongyan.activity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.wftd.kongyan.R;
import com.wftd.kongyan.app.UserHelper;
import com.wftd.kongyan.base.BaseActivity;
import com.wftd.kongyan.callback.VersionCallback;
import com.wftd.kongyan.entity.Ser1UserInfo;
import com.wftd.kongyan.entity.Version;
import com.wftd.kongyan.util.HttpUtils;

/**
 * @author AndSync
 * @date 2018/6/28
 * Copyright © 2014-2018 AndSync All rights reserved.
 */
public class HomePageActivity extends BaseActivity implements View.OnClickListener {

    private ImageView iv_people;
    private TextView iv_wenjuan;
    private TextView iv_shuju;
    private Ser1UserInfo user = UserHelper.getUserInfo();
    private RelativeLayout layout_version;

    private Version versionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        layout_version = (RelativeLayout) findViewById(R.id.layout_version);
        iv_people = (ImageView) findViewById(R.id.iv_people);
        iv_wenjuan = (TextView) findViewById(R.id.tv_wenjuan);
        iv_shuju = (TextView) findViewById(R.id.tv_shuju);
        iv_people.setOnClickListener(this);
        iv_wenjuan.setOnClickListener(this);
        iv_shuju.setOnClickListener(this);
        layout_version.setOnClickListener(this);
        HttpUtils.appUpdate(new VersionCallback() {
            @Override
            public boolean success(final Version version) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        versionInfo = version;
                        if (version.hasNewVersion(HomePageActivity.this)) {
                            findViewById(R.id.tv_new).setVisibility(View.VISIBLE);
                        } else {
                            findViewById(R.id.tv_new).setVisibility(View.GONE);
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
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.iv_people:
                Intent intent = new Intent(this, PersonalCenterActivity.class);
                startActivity(intent);
                break;
            case R.id.tv_wenjuan:
                Intent intent2 = new Intent(this, BloodMeasureActivity.class);
                startActivity(intent2);
                break;
            case R.id.tv_shuju:
                Intent intent3 = new Intent(this, DataUpActivity.class);
                startActivity(intent3);
                break;
            case R.id.layout_version:
                if (versionInfo != null && versionInfo.hasNewVersion(context)) {
                    Uri uri = Uri.parse(versionInfo.getUrl());
                    Intent intentApp = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intentApp);
                }
                break;
            default:
                break;
        }
    }
}
