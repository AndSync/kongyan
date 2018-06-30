package com.wftd.kongyan.util;

import android.content.Context;
import android.widget.Toast;

/**
 * toast
 */
public class ToastUtils {
    public static void show(Context context, String content) {
        Toast.makeText(context, content, Toast.LENGTH_SHORT).show();
    }
}
