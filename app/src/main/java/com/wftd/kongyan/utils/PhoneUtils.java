package com.wftd.kongyan.utils;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 手机相关操作
 */
public class PhoneUtils {
    /**
     * 检测当的网络（Wifi/GPRS）状态
     *
     * @return true 表示网络可用
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo info = connectivity.getActiveNetworkInfo();
            if (info != null && info.isConnected()) {
                // 当前网络是连接的
                if (info.getState() == NetworkInfo.State.CONNECTED) {
                    // 当前所连接的网络可用
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 跳转到系统设置界面
     */
    public static void openSettings(Context context) {
        Intent i = new Intent();
        if (android.os.Build.VERSION.SDK_INT >= 11) {
            i.setClassName("com.android.settings", "com.android.settings.Settings$WifiSettingsActivity");
        } else {
            i.setClassName("com.android.settings"
                    , "com.android.settings.wifi.WifiSettings");
        }
        context.startActivity(i);
    }

}
