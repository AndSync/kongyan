package com.wftd.kongyan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.wftd.kongyan.R;
import com.wftd.kongyan.util.CommonUtils;
import com.wftd.kongyan.util.ToastUtils;
import com.wftd.kongyan.view.PowerfulEditText;

/**
 * 修改密码弹框
 */
public class ModifyPasswordDialog {
    private static ModifyPasswordDialog mModifyPasswordDialog;
    private Context mContext;

    private ModifyPasswordDialog(Context context) {
        mContext = context;
    }

    public static ModifyPasswordDialog getInstance(Context context) {
        if (mModifyPasswordDialog == null) {
            synchronized (ModifyPasswordDialog.class) {
                if (mModifyPasswordDialog == null) {
                    mModifyPasswordDialog = new ModifyPasswordDialog(context);
                }
            }
        }
        return mModifyPasswordDialog;
    }

    private Dialog mDialog;

    public Dialog build() {
        if (mDialog != null) {
            return mDialog;
        }
        mDialog = new Dialog(mContext, R.style.myDialog);
        mDialog.setCancelable(false);
        mDialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(mContext).inflate(R.layout.modify_password_dialog, null);

        final PowerfulEditText oldPassword = (PowerfulEditText) view.findViewById(R.id.old_password);
        final PowerfulEditText newPassword = (PowerfulEditText) view.findViewById(R.id.new_password);
        final PowerfulEditText again = (PowerfulEditText) view.findViewById(R.id.again);
        TextView confirm = (TextView) view.findViewById(R.id.confirm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldP = oldPassword.getText().toString();
                String newP = newPassword.getText().toString();
                String againP = again.getText().toString();
                if (oldP.length() == 0 || newP.length() == 0 || againP.length() == 0) {
                    ToastUtils.show(mContext, "请输入密码");
                    return;
                }
                if ((oldP.length() < 6 && oldP.length() > 0) || (newP.length() < 6 && newP.length() > 0) || (againP.length() < 6 && againP.length() > 0)) {
                    ToastUtils.show(mContext, "密码格式错误");
                    return;
                }
                if (!CommonUtils.isPassWord(oldP) || !CommonUtils.isPassWord(newP) || !CommonUtils.isPassWord(againP)) {
                    ToastUtils.show(mContext, "密码错误");
                    return;
                }
                if (!newP.equals(againP)) {
                    ToastUtils.show(mContext, "前后输入密码不一致");
                    return;
                }
                //TODO 修改密码
                mDialog.dismiss();
            }
        });
        mDialog.setContentView(view);
        mDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (null != mDialog) {
                    mDialog = null;
                }
            }
        });
        return mDialog;
    }

    public void show() {
        if (mDialog.isShowing()) {
            return;
        }
        mDialog.show();
    }

    public void dismiss() {
        if (mDialog.isShowing()) {
            mDialog.dismiss();
        }
    }
}
