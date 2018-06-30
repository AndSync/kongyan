package com.wftd.kongyan.entity;

import android.content.Context;
import com.wftd.kongyan.util.AppUtils;

/**
 * @author AndSync
 * @date 2018/6/29
 * Copyright © 2014-2018 AndSync All rights reserved.
 */
public class Version {

    private int versionCode;
    private String url;

    public int getVersionCode() {
        return versionCode;
    }

    public void setVersionCode(int versionCode) {
        this.versionCode = versionCode;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public boolean hasNewVersion(Context context){
        return versionCode>AppUtils.getVersionCode(context);
    }
}
