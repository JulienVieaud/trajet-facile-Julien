package com.poe.trajetfacile.business.delegate;

import java.time.Duration;
import java.time.LocalDateTime;

public class BookingDelegateService {

    public static final int CANCELATION_MAX_DELAY_IN_HOURS = 24;

    /**
     * On ne peut annuler une réservation qu'à certaines conditions.
     */
    public static boolean isCancelable(LocalDateTime desiredCancelationDate, LocalDateTime rideStartDate) {
        boolean isCancelable = true;
        long duration = Duration.between(desiredCancelationDate, rideStartDate).toMinutes();
        long delayInMinutes = (((long) BookingDelegateService.CANCELATION_MAX_DELAY_IN_HOURS) * 60l); // convertion du délai en minutes
        isCancelable = duration > delayInMinutes;
        return isCancelable;
    }
}
