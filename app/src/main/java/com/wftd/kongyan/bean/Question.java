package com.wftd.kongyan.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * 调查问卷
 */
@Table(name = "question")
public class Question implements java.io.Serializable {
    @Column(name = "id", isId = true, autoGen = true)
    private int id;
    @Column(name = "isUpdate")
    private boolean isUpdate;
    @Column(name = "name")
    private String name;
    @Column(name = "sex")
    private int sex;
    @Column(name = "age")
    private int age;
    @Column(name = "ageType")
    private int ageType;
    @Column(name = "district")
    private String district;
    @Column(name = "systolicPressure")
    private int systolicPressure;
    @Column(name = "systolicPressureType")
    private int systolicPressureType;
    @Column(name = "diastolicPressure")
    private int diastolicPressure;
    @Column(name = "diastolicPressureType")
    private int diastolicPressureType;
    @Column(name = "saltThreshold")
    private int saltThreshold;
    @Column(name = "patientType")
    private int patientType;
    @Column(name = "takeDrugsA")
    private Integer takeDrugsA;
    @Column(name = "takeDrugsB")
    private Integer takeDrugsB;
    @Column(name = "takeDrugsC")
    private Integer takeDrugsC;
    @Column(name = "takeDrugsD")
    private Integer takeDrugsD;
    @Column(name = "takeDrugsE")
    private Integer takeDrugsE;
    @Column(name = "takeDrugsF")
    private Integer takeDrugsF;
    @Column(name = "takeDrugsG")
    private Integer takeDrugsG;
    @Column(name = "takeDrugsType")
    private int takeDrugsType;
    @Column(name = "takeDrugs1Type")
    private int takeDrugs1Type;
    @Column(name = "takeDrugs2Type")
    private int takeDrugs2Type;
    @Column(name = "takeDrugs3Type")
    private int takeDrugs3Type;
    @Column(name = "q1")
    private Integer q1;
    @Column(name = "q2")
    private Integer q2;
    @Column(name = "q3")
    private Integer q3;
    @Column(name = "q4")
    private Integer q4;
    @Column(name = "q5")
    private Integer q5;
    @Column(name = "q6")
    private Integer q6;
    @Column(name = "q7")
    private Integer q7;
    @Column(name = "q8")
    private Integer q8;
    @Column(name = "q9")
    private Integer q9;
    @Column(name = "q10")
    private Integer q10;
    @Column(name = "q11")
    private Integer q11;
    @Column(name = "q12")
    private Integer q12;
    @Column(name = "q13")
    private Integer q13;
    @Column(name = "score")
    private Integer score;
    @Column(name = "scoreType")
    private int scoreType;
    @Column(name = "peopleId")
    private String peopleId;
    @Column(name = "doctorId")
    private String doctorId;
    @Column(name = "organizationId")
    private String organizationId;
    @Column(name = "submitDate")
    private String submitDate;
    @Column(name = "isReturnVisit")
    private int isReturnVisit;
    @Column(name = "returnVisitDate")
    private String returnVisitDate;


    public Question() {
    }

    public Question(int id, String name, int sex, int age, int systolicPressure, int diastolicPressure,
                    int saltThreshold) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.systolicPressure = systolicPressure;
        this.diastolicPressure = diastolicPressure;
        this.saltThreshold = saltThreshold;
    }

    public Question(int id, String name, int sex, int age, int ageType, String district,
                    int systolicPressure, int systolicPressureType, int diastolicPressure, int diastolicPressureType,
                    int saltThreshold, int patientType, Integer takeDrugsA, Integer takeDrugsB, Integer takeDrugsC,
                    Integer takeDrugsD, Integer takeDrugsE, Integer takeDrugsF, Integer takeDrugsG, int takeDrugsType,
                    int takeDrugs1Type, int takeDrugs2Type, int takeDrugs3Type, Integer q1, Integer q2, Integer q3,
                    Integer q4, Integer q5, Integer q6, Integer q7, Integer q8, Integer q9, Integer q10, Integer q11,
                    Integer q12, Integer q13, Integer score, int scoreType, String peopleId, String doctorId,
                    String organizationId, String submitDate, int isReturnVisit, String returnVisitDate) {
        this.id = id;
        this.name = name;
        this.sex = sex;
        this.age = age;
        this.ageType = ageType;
        this.district = district;
        this.systolicPressure = systolicPressure;
        this.systolicPressureType = systolicPressureType;
        this.diastolicPressure = diastolicPressure;
        this.diastolicPressureType = diastolicPressureType;
        this.saltThreshold = saltThreshold;
        this.patientType = patientType;
        this.takeDrugsA = takeDrugsA;
        this.takeDrugsB = takeDrugsB;
        this.takeDrugsC = takeDrugsC;
        this.takeDrugsD = takeDrugsD;
        this.takeDrugsE = takeDrugsE;
        this.takeDrugsF = takeDrugsF;
        this.takeDrugsG = takeDrugsG;
        this.takeDrugsType = takeDrugsType;
        this.takeDrugs1Type = takeDrugs1Type;
        this.takeDrugs2Type = takeDrugs2Type;
        this.takeDrugs3Type = takeDrugs3Type;
        this.q1 = q1;
        this.q2 = q2;
        this.q3 = q3;
        this.q4 = q4;
        this.q5 = q5;
        this.q6 = q6;
        this.q7 = q7;
        this.q8 = q8;
        this.q9 = q9;
        this.q10 = q10;
        this.q11 = q11;
        this.q12 = q12;
        this.q13 = q13;
        this.score = score;
        this.scoreType = scoreType;
        this.peopleId = peopleId;
        this.doctorId = doctorId;
        this.organizationId = organizationId;
        this.submitDate = submitDate;
        this.isReturnVisit = isReturnVisit;
        this.returnVisitDate = returnVisitDate;
    }

    public boolean isUpdate() {
        return isUpdate;
    }

    public void setUpdate(boolean update) {
        isUpdate = update;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int isSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return this.age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getAgeType() {
        return this.ageType;
    }

    public void setAgeType(int ageType) {
        this.ageType = ageType;
    }

    public String getDistrict() {
        return this.district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getSystolicPressure() {
        return this.systolicPressure;
    }

    public void setSystolicPressure(int systolicPressure) {
        this.systolicPressure = systolicPressure;
    }

    public int getSystolicPressureType() {
        return this.systolicPressureType;
    }

    public void setSystolicPressureType(int systolicPressureType) {
        this.systolicPressureType = systolicPressureType;
    }

    public int getDiastolicPressure() {
        return this.diastolicPressure;
    }

    public void setDiastolicPressure(int diastolicPressure) {
        this.diastolicPressure = diastolicPressure;
    }

    public int getDiastolicPressureType() {
        return this.diastolicPressureType;
    }

    public void setDiastolicPressureType(int diastolicPressureType) {
        this.diastolicPressureType = diastolicPressureType;
    }

    public int getSaltThreshold() {
        return this.saltThreshold;
    }

    public void setSaltThreshold(int saltThreshold) {
        this.saltThreshold = saltThreshold;
    }

    public int getPatientType() {
        return this.patientType;
    }

    public void setPatientType(int patientType) {
        this.patientType = patientType;
    }

    public Integer getTakeDrugsA() {
        return this.takeDrugsA;
    }

    public void setTakeDrugsA(Integer takeDrugsA) {
        this.takeDrugsA = takeDrugsA;
    }

    public Integer getTakeDrugsB() {
        return this.takeDrugsB;
    }

    public void setTakeDrugsB(Integer takeDrugsB) {
        this.takeDrugsB = takeDrugsB;
    }

    public Integer getTakeDrugsC() {
        return this.takeDrugsC;
    }

    public void setTakeDrugsC(Integer takeDrugsC) {
        this.takeDrugsC = takeDrugsC;
    }

    public Integer getTakeDrugsD() {
        return this.takeDrugsD;
    }

    public void setTakeDrugsD(Integer takeDrugsD) {
        this.takeDrugsD = takeDrugsD;
    }

    public Integer getTakeDrugsE() {
        return this.takeDrugsE;
    }

    public void setTakeDrugsE(Integer takeDrugsE) {
        this.takeDrugsE = takeDrugsE;
    }

    public Integer getTakeDrugsF() {
        return this.takeDrugsF;
    }

    public void setTakeDrugsF(Integer takeDrugsF) {
        this.takeDrugsF = takeDrugsF;
    }

    public Integer getTakeDrugsG() {
        return this.takeDrugsG;
    }

    public void setTakeDrugsG(Integer takeDrugsG) {
        this.takeDrugsG = takeDrugsG;
    }

    public int getTakeDrugsType() {
        return this.takeDrugsType;
    }

    public void setTakeDrugsType(int takeDrugsType) {
        this.takeDrugsType = takeDrugsType;
    }

    public int getTakeDrugs1Type() {
        return this.takeDrugs1Type;
    }

    public void setTakeDrugs1Type(int takeDrugs1Type) {
        this.takeDrugs1Type = takeDrugs1Type;
    }

    public int getTakeDrugs2Type() {
        return this.takeDrugs2Type;
    }

    public void setTakeDrugs2Type(int takeDrugs2Type) {
        this.takeDrugs2Type = takeDrugs2Type;
    }

    public int getTakeDrugs3Type() {
        return this.takeDrugs3Type;
    }

    public void setTakeDrugs3Type(int takeDrugs3Type) {
        this.takeDrugs3Type = takeDrugs3Type;
    }

    public Integer getQ1() {
        return this.q1;
    }

    public void setQ1(Integer q1) {
        this.q1 = q1;
    }

    public Integer getQ2() {
        return this.q2;
    }

    public void setQ2(Integer q2) {
        this.q2 = q2;
    }

    public Integer getQ3() {
        return this.q3;
    }

    public void setQ3(Integer q3) {
        this.q3 = q3;
    }

    public Integer getQ4() {
        return this.q4;
    }

    public void setQ4(Integer q4) {
        this.q4 = q4;
    }

    public Integer getQ5() {
        return this.q5;
    }

    public void setQ5(Integer q5) {
        this.q5 = q5;
    }

    public Integer getQ6() {
        return this.q6;
    }

    public void setQ6(Integer q6) {
        this.q6 = q6;
    }

    public Integer getQ7() {
        return this.q7;
    }

    public void setQ7(Integer q7) {
        this.q7 = q7;
    }

    public Integer getQ8() {
        return this.q8;
    }

    public void setQ8(Integer q8) {
        this.q8 = q8;
    }

    public Integer getQ9() {
        return this.q9;
    }

    public void setQ9(Integer q9) {
        this.q9 = q9;
    }

    public Integer getQ10() {
        return this.q10;
    }

    public void setQ10(Integer q10) {
        this.q10 = q10;
    }

    public Integer getQ11() {
        return this.q11;
    }

    public void setQ11(Integer q11) {
        this.q11 = q11;
    }

    public Integer getQ12() {
        return this.q12;
    }

    public void setQ12(Integer q12) {
        this.q12 = q12;
    }

    public Integer getQ13() {
        return this.q13;
    }

    public void setQ13(Integer q13) {
        this.q13 = q13;
    }

    public Integer getScore() {
        return this.score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public int getScoreType() {
        return this.scoreType;
    }

    public void setScoreType(int scoreType) {
        this.scoreType = scoreType;
    }

    public String getPeopleId() {
        return this.peopleId;
    }

    public void setPeopleId(String peopleId) {
        this.peopleId = peopleId;
    }

    public String getDoctorId() {
        return this.doctorId;
    }

    public void setDoctorId(String doctorId) {
        this.doctorId = doctorId;
    }

    public String getOrganizationId() {
        return this.organizationId;
    }

    public void setOrganizationId(String organizationId) {
        this.organizationId = organizationId;
    }

    public String getSubmitDate() {
        return this.submitDate;
    }

    public void setSubmitDate(String submitDate) {
        this.submitDate = submitDate;
    }

    public int getIsReturnVisit() {
        return this.isReturnVisit;
    }

    public void setIsReturnVisit(int isReturnVisit) {
        this.isReturnVisit = isReturnVisit;
    }

    public String getReturnVisitDate() {
        return this.returnVisitDate;
    }

    public void setReturnVisitDate(String returnVisitDate) {
        this.returnVisitDate = returnVisitDate;
    }

}
