package com.wftd.kongyan.util;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.content.ContextCompat;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;

/**
 * View相关工具类
 *
 * @author AndSync
 * @date 2017/10/18
 * Copyright © 2014-2017 北京智阅网络科技有限公司 All rights reserved.
 */
public class UiHelper {

    public static void addDrawableRight(Context context, TextView textView, int resId) {
        Drawable arrow = ContextCompat.getDrawable(context, resId);
        arrow.setBounds(0, 0, arrow.getIntrinsicWidth(), arrow.getIntrinsicHeight());
        textView.setCompoundDrawables(null, null, arrow, null);
    }

    public static void addDrawableLeft(Context context, TextView textView, int resId) {
        Drawable drawable = ContextCompat.getDrawable(context, resId);
        drawable.setBounds(0, 0, drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight());
        textView.setCompoundDrawables(drawable, null, null, null);
    }

    public static void addDrawableTop(Context context, TextView textView, int resId) {
        Drawable arrow = ContextCompat.getDrawable(context, resId);
        arrow.setBounds(0, 0, arrow.getIntrinsicWidth(), arrow.getIntrinsicHeight());
        textView.setCompoundDrawables(null, arrow, null, null);
    }

    public static void hideSoftKeyboard(final Activity context) {
        new Handler(Looper.getMainLooper()).post(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                if (context.getCurrentFocus() != null) {
                    if (context.getCurrentFocus().getWindowToken() != null) {
                        imm.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
                            InputMethodManager.HIDE_NOT_ALWAYS);
                    }
                }
            }
        });
    }

    public static void showSoftKeyboard(final EditText editText) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager inputManager =
                    (InputMethodManager) editText.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.showSoftInput(editText, 0);
            }
        }, 300);
    }

    public static void showSoftKeyboard(final Context context) {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
            }
        }, 300);
    }
}
