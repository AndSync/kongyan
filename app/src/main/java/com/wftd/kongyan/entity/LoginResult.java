package com.wftd.kongyan.entity;

import java.io.Serializable;
import java.util.List;

/**
 * @author AndSync
 * @date 2018/6/29
 * Copyright © 2014-2018 AndSync All rights reserved.
 */
public class LoginResult implements Serializable {
    private static final long serialVersionUID = 6582181405644630863L;
    private List<Doctor> doctors;
    private People people;

    public List<Doctor> getDoctors() {
        return doctors;
    }

    public void setDoctors(List<Doctor> doctors) {
        this.doctors = doctors;
    }

    public People getPeople() {
        return people;
    }

    public void setPeople(People people) {
        this.people = people;
    }
}
