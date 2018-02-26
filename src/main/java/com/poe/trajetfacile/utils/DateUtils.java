package com.poe.trajetfacile.utils;

import java.util.Calendar;
import java.util.Date;

/**
 * Utilitaires pour la manipulation de dates.
 */
public class DateUtils {

    /**
     * Ajoute la précision à la minute sur une date donnée.
     * @param datePrecisionToDay
     * @param hours
     * @param minutes
     * @return une date précise (à la minute)
     */
    public static Date convert(Date datePrecisionToDay, short hours, short minutes) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(datePrecisionToDay);
        cal.set(Calendar.HOUR, hours);
        cal.set(Calendar.MINUTE, minutes);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return cal.getTime();
    }
}
