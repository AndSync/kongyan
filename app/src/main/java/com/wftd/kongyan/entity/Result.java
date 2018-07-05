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

    public static Result getResult(Question question, int mNumber) {
        Result result1 = null;
        String healthTip2 = "处于正常范围";
        boolean isHighBlood = false;
        if (question.getAge() <= 65) {
            if (question.getSystolicPressure() > 140 || question.getDiastolicPressure() > 90) {
                isHighBlood = true;
            }
        } else {
            if (question.getSystolicPressure() > 150 || question.getDiastolicPressure() > 90) {
                isHighBlood = true;
            }
        }
        if (isHighBlood) {
            healthTip2 = "超出正常范围，偏高";
        }
        String healthTip3 = "";
        if (mNumber < 9) {
            if (isHighBlood) {
                healthTip3 = "请保持清淡饮食，建议咨询门诊医生是否需要调整降压治疗";
            } else {
                healthTip3 = "保持清淡饮食，合理膳食";
            }
            result1 = new Result(question.getName(), question.getSex() == 1 ? "先生" : "女士",
                question.getSystolicPressure() + "/" + question.getDiastolicPressure() + " mmHg", "30%", mNumber + "",
                "低盐（食盐摄入量合适）- " + healthTip2 + " - " + healthTip3 + "");
        } else if (9 <= mNumber && mNumber <= 13) {
            if (isHighBlood) {
                healthTip3 = "请保持清淡饮食，建议咨询门诊医生是否需要调整降压治疗";
            } else {
                healthTip3 = "保持清淡饮食，合理膳食";
            }
            result1 = new Result(question.getName(), question.getSex() == 1 ? "先生" : "女士",
                question.getSystolicPressure() + "/" + question.getDiastolicPressure() + " mmHg", "30%", mNumber + "",
                "正常（食盐摄入量合适）- " + healthTip2 + "  - " + healthTip3 + "");
        } else if (14 <= mNumber && mNumber <= 19) {
            if (isHighBlood) {
                healthTip3 = "请咨询门诊医生是否需要调整您的饮食习惯，您的血压水平是否合适，以获得更恰当的治疗";
            } else {
                healthTip3 = "请咨询门诊医生是否需要调整您的饮食习惯，建议您定期测量血压";
            }
            result1 = new Result(question.getName(), question.getSex() == 1 ? "先生" : "女士",
                question.getSystolicPressure() + "/" + question.getDiastolicPressure() + " mmHg", "30%", mNumber + "",
                "中盐（食盐摄入量偏高）- " + healthTip2 + "  - " + healthTip3 + "");
        } else if (20 <= mNumber) {
            if (isHighBlood) {
                healthTip3 = "请咨询门诊医生是否需要调整您的饮食习惯，您的血压水平是否合适，以获得更恰当的治疗";
            } else {
                healthTip3 = "请咨询门诊医生是否需要调整您的饮食习惯，建议您定期测量血压";
            }
            result1 = new Result(question.getName(), question.getSex() == 1 ? "先生" : "女士",
                question.getSystolicPressure() + "/" + question.getDiastolicPressure() + " mmHg", "30%", mNumber + "",
                "高盐（食盐摄入量偏高）- " + healthTip2 + "  - " + healthTip3 + "");
        }
        return result1;
    }
}
