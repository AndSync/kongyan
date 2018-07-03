package com.wftd.kongyan.app;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.text.TextUtils;
import android.util.Log;
import com.ihealth.communication.manager.iHealthDevicesManager;
import com.tencent.bugly.crashreport.CrashReport;
import com.wftd.kongyan.entity.People;
import com.wftd.kongyan.util.AidlUtil;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import org.xutils.x;

/**
 * @author AndSync
 * @date 2018/6/30
 * Copyright © 2014-2018 AndSync All rights reserved.
 */
public class App extends Application {
    private static App mApp;
    private ArrayList<Activity> mList;
    private static Context context;

    public static People loginUser = new People();

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        mApp = this;
        mList = new ArrayList<>();
        AidlUtil.getInstance().connectPrinterService(this);
        iHealthDevicesManager.getInstance().init(this, Log.VERBOSE, Log.ASSERT);
        try {
            InputStream is = getAssets().open("com_wftd_kongyan_android.pem");
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            boolean isPass = iHealthDevicesManager.getInstance().sdkAuthWithLicense(buffer);

            //xUtils3初始化
            x.Ext.init(this);
            //是否打开log
            x.Ext.setDebug(true);
        } catch (IOException e) {
            e.printStackTrace();
        }  //写入权限的
        initCrashReport();
    }

    public static Context getContext() {
        return context;
    }

    public static App getInstance() {
        return mApp;
    }

    public void add(Activity activity) {
        mList.add(activity);
    }

    public void remove(Activity activity) {
        mList.remove(activity);
    }

    public void exit() {
        for (Activity activity : mList) {
            activity.finish();
        }
    }

    private void initCrashReport() {
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(context);
        strategy.setAppChannel("kongyan");
        CrashReport.initCrashReport(context, "50208299f2", ServerConfig.DEBUG, strategy);
        String userId = UserHelper.getUserInfo().getId();
        if (!TextUtils.isEmpty(userId)) {
            CrashReport.setUserId(UserHelper.getUserInfo().getId());
        }
    }
}
