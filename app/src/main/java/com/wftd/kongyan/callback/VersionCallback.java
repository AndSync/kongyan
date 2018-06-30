package com.wftd.kongyan.callback;

import com.wftd.kongyan.bean.Version;

/**
 */
public interface VersionCallback {
    boolean success(Version version);

    boolean fail();
}
