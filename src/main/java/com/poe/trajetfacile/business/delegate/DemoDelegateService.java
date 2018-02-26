package com.poe.trajetfacile.business.delegate;

import java.time.LocalDate;
import java.time.Period;

public class DemoDelegateService {

    public static final int AGE_MAJORITEE_FRANCE = 18;

    public static boolean estMajeurEnFrance(LocalDate dateNaissance) {
        int age = Period.between(dateNaissance, LocalDate.now()).getYears();
        return age >= DemoDelegateService.AGE_MAJORITEE_FRANCE;
    }
}
