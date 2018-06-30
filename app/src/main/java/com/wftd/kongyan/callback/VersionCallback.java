package com.wftd.kongyan.callback;

import com.wftd.kongyan.entity.Version;

/**
 */
public interface VersionCallback {
    boolean success(Version version);

    boolean fail();
}
