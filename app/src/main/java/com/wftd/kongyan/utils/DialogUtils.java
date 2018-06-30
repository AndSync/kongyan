package com.wftd.kongyan.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.Html;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.wftd.kongyan.R;

/**
 * 弹窗工具类
 *
 * @author AndSync
 * @date 2017/10/18
 * Copyright © 2014-2017 AndSync All rights reserved.
 */
public class DialogUtils {

    public static Dialog showAlertDialog(Context context, String title, String content) {
        final Dialog dialog = showCenterDialog(context, R.layout.dialog_alert);
        TextView tvTitle = (TextView) dialog.findViewById(R.id.tv_title);
        TextView tvContent = (TextView) dialog.findViewById(R.id.tv_content);
        ImageView ivSure = (ImageView) dialog.findViewById(R.id.iv_sure);
        tvTitle.setText(Html.fromHtml(title));
        tvContent.setText(Html.fromHtml(content));
        ivSure.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        return dialog;
    }

    public static Dialog showCenterDialog(Context context, int resource) {
        Dialog mDialog = new Dialog(context, R.style.dialog_custom);
        if (context instanceof Activity && !((Activity) context).isFinishing()) {
            View dialogView = View.inflate(context, resource, null);
            mDialog.addContentView(dialogView,
                new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            mDialog.show();
        }
        return mDialog;
    }
}

