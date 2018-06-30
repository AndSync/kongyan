package com.wftd.kongyan.base;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;
import com.gyf.barlibrary.ImmersionBar;
import com.wftd.kongyan.app.App;
import com.wftd.kongyan.app.Constant;
import com.wftd.kongyan.db.DbConfig;
import com.wftd.kongyan.util.UiHelper;
import org.xutils.DbManager;
import org.xutils.x;

/**
 * activity基类
 */
public class BaseActivity extends Activity {
    protected String TAG = getClass().getSimpleName();
    public DbManager db = null;
    protected Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        context = this;
        App.getInstance().add(this);
        ImmersionBar.with(this).init();
        db = x.getDb(DbConfig.getDaoConfig());
        db.getDatabase();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        App.getInstance().remove(this);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == Constant.FINISH_ACTIVITY && resultCode == RESULT_OK) {
            finishActivity();
        }
    }

    protected void finishActivity() {
        ((Activity) context).setResult(RESULT_OK);
        finish();
    }

    @Override
    protected void onPause() {
        super.onPause();
        UiHelper.hideSoftKeyboard(this);
    }
}
