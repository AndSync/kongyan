package com.wftd.kongyan.callback;

import com.wftd.kongyan.entity.Doctor;
import java.util.List;

/**
 */
public interface DoctorCallback {
    boolean success(List<Doctor> doctors);

    boolean fail();
}
