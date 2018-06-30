package com.wftd.kongyan.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by liwei on 2018/6/17.
 */

public class Doctor implements Parcelable {

    /**
     * id : 60b4ca6b9ea64342aec4f42a987fc98e
     * organizationId : 010-001
     * name : 李四
     */

    private String id;
    private String organizationId;
    private String name;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getOrganizationId() {
        return organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {

    }
}
