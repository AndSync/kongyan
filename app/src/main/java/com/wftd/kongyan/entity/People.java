package com.wftd.kongyan.entity;

import android.text.TextUtils;
import java.io.Serializable;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 用户
 */
@Table(name = "people")
public class People implements Serializable {
    @Column(name = "id", isId = true, autoGen = false)
    private String id;
    @Column(name = "organizationId")
    private String organizationId;
    @Column(name = "name")
    private String name;
    @Column(name = "password")
    private String password;
    @Column(name = "passwordText")
    private String passwordText;
    @Column(name = "type")
    private int type;
    @Column(name = "remark")
    private String remark;
    @Column(name = "isDelete")
    private int isDelete;
    @Column(name = "phoneNumber") //手机号
    private String phoneNumber;
    @Column(name = "post")
    private String post;
    @Column(name = "orgnizationName")
    private String orgnizationName;

    public String getOrgnizationName() {
        return orgnizationName;
    }

    public void setOrgnizationName(String orgnizationName) {
        this.orgnizationName = orgnizationName;
    }

    public String getPost() {
        return post;
    }

    public String getPostStr() {
        if (TextUtils.equals(post, "1")) {
            return "医生";
        }
        if (TextUtils.equals(post, "2")) {
            return "护士";
        }
        return "暂无数据";
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPasswordText() {
        return passwordText;
    }

    public void setPasswordText(String passwordText) {
        this.passwordText = passwordText;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(int isDelete) {
        this.isDelete = isDelete;
    }

    public boolean isLogin() {
        return !TextUtils.isEmpty(name);
    }

    @Override
    public String toString() {
        return "People{"
            + "id='"
            + id
            + '\''
            + ", organizationId='"
            + organizationId
            + '\''
            + ", name='"
            + name
            + '\''
            + ", password='"
            + password
            + '\''
            + ", passwordText='"
            + passwordText
            + '\''
            + ", type="
            + type
            + ", remark='"
            + remark
            + '\''
            + ", isDelete="
            + isDelete
            + ", phoneNumber='"
            + phoneNumber
            + '\''
            + ", post="
            + post
            + ", orgnizationName='"
            + orgnizationName
            + '\''
            + '}';
    }
}
