package com.poe.trajetfacile.api;

import com.poe.trajetfacile.business.service.BookingService;
import com.poe.trajetfacile.domain.Booking;
import com.poe.trajetfacile.exception.RideIsFullBusinessException;
import com.poe.trajetfacile.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/booking")
public class BookingApiController {

    @Autowired
    BookingService bookingService;

    @Autowired
    BookingRepository bookingRepository;

    @GetMapping
    public Iterable<Booking> list() {
        return bookingRepository.findAll();
    }

    @PostMapping("{userId}/{rideId}")
    public void save(@PathVariable("userId") Long userId, @PathVariable("rideId") Long rideId) throws RideIsFullBusinessException {
        bookingService.bookARide(userId, rideId);
    }

}
