package com.wftd.kongyan.util;

import android.util.DisplayMetrics;
import com.wftd.kongyan.app.App;

/**
 * 设备显示工具类
 *
 * @author AndSync
 * @date 2017/10/18
 * Copyright © 2014-2017 AndSync All rights reserved.
 */
public class DisplayUtils {

    /**
     * 获取屏幕宽度
     */
    public static int getScreenWidth() {
        DisplayMetrics dm = App.getContext().getResources().getDisplayMetrics();
        return dm.widthPixels;
    }

    /**
     * 获取屏幕高度
     */
    public static int getScreenHeight() {
        DisplayMetrics dm = App.getContext().getResources().getDisplayMetrics();
        return dm.heightPixels;
    }

    /**
     * 将dp转换为px值，保证尺寸大小不变
     */
    public static int dp2px(float dpValue) {
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    /**
     * 将px值转换为dp，保证尺寸大小不变
     */
    public static int px2dp(float pxValue) {
        final float scale = App.getContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     */
    public static int px2sp(float pxValue) {
        final float fontScale = App.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将sp值转换为px值，保证文字大小不变
     */
    public static int sp2px(float spValue) {
        final float fontScale = App.getContext().getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }
}