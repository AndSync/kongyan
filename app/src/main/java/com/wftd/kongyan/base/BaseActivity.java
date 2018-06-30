package com.wftd.kongyan.base;


import android.app.Activity;
import android.os.Bundle;
import android.view.Window;

import com.gyf.barlibrary.ImmersionBar;
import com.wftd.kongyan.app.App;
import com.wftd.kongyan.db.DbConfig;

import org.xutils.DbManager;
import org.xutils.x;

/**
 * activity基类
 */
public class BaseActivity extends Activity {

    public DbManager db = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        App.getInstance().add(this);
        ImmersionBar.with(this).init();
        db = x.getDb(DbConfig.getDaoConfig());
        db.getDatabase();
    }

    @Override
    protected void onDestroy() {
        App.getInstance().remove(this);
        super.onDestroy();
    }
}
