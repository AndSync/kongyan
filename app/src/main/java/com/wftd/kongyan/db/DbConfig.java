package com.wftd.kongyan.db;

import android.os.Environment;

import org.xutils.DbManager;

import java.io.File;

/**
 * Created by liwei on 2018/6/17.
 */

public class DbConfig {
    private static DbManager.DaoConfig daoConfig;

    public static DbManager.DaoConfig getDaoConfig(){
        File file = new File(Environment.getExternalStorageDirectory().getPath());

        if(daoConfig == null){
            daoConfig = new DbManager.DaoConfig()
                    .setDbName("kongyan.db")   //设置数据库的名字
                    .setDbVersion(1)            //设置数据的版本号
                    .setAllowTransaction(true)  //设置是否允许开启事务
                    .setDbUpgradeListener(new DbManager.DbUpgradeListener() {//设置一个数据库版本升级的监听
                        @Override
                        public void onUpgrade(DbManager db, int oldVersion, int newVersion) {

                        }
                    });
        }
        return daoConfig;
    }
}
