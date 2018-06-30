package com.wftd.kongyan.dialog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;

import com.wftd.kongyan.R;

/**
 * 调查问卷结果
 */
public class QuestionnaireResultDialog {

    private static QuestionnaireResultDialog mQuestionnaireResultDialog;
    private Context mContext;

    private QuestionnaireResultDialog(Context context) {
        mContext = context;
    }
    public static QuestionnaireResultDialog getInstance(Context context) {
        if (mQuestionnaireResultDialog == null) {
            synchronized (QuestionnaireResultDialog.class) {
                if (mQuestionnaireResultDialog == null) {
                    mQuestionnaireResultDialog = new QuestionnaireResultDialog(context);
                }
            }
        }
        return mQuestionnaireResultDialog;
    }

    private Dialog mBacDialog;

    public Dialog build() {
        if(mBacDialog != null){
            return mBacDialog;
        }
        mBacDialog = new Dialog(mContext, R.style.myDialog);
        mBacDialog.setCancelable(false);
        mBacDialog.setCanceledOnTouchOutside(true);
        View view = LayoutInflater.from(mContext).inflate(R.layout.questionnaire_dialog, null);
        mBacDialog.setContentView(view);
        mBacDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialogInterface) {
                if (null != mBacDialog) {
                    mBacDialog = null;
                }
            }
        });
        return mBacDialog;
    }
    public void show() {
        if(mBacDialog.isShowing()){
            return;
        }
        mBacDialog.show();
    }
    public void dismiss() {
        if(mBacDialog.isShowing()){
            mBacDialog.dismiss();
        }
    }
}
