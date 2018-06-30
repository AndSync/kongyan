package com.wftd.kongyan.entity;

import java.io.Serializable;

/**
 * 调查结果
 */
public class Result implements Serializable {
    private String name;
    private String sex;
    private String blood;
    private String salt;
    private String score;
    private String healthTip;

    public Result(String name, String sex, String blood, String salt, String score, String healthTip) {
        this.name = name;
        this.sex = sex;
        this.blood = blood;
        this.salt = salt;
        this.score = score;
        this.healthTip = healthTip;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getBlood() {
        return blood;
    }

    public void setBlood(String blood) {
        this.blood = blood;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public String getScore() {
        return score;
    }

    public void setScore(String score) {
        this.score = score;
    }

    public String getHealthTip() {
        return healthTip;
    }

    public void setHealthTip(String healthTip) {
        this.healthTip = healthTip;
    }
}
