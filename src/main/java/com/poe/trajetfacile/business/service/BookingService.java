package com.poe.trajetfacile.business.service;

import com.poe.trajetfacile.business.delegate.BookingDelegateService;
import com.poe.trajetfacile.domain.Booking;
import com.poe.trajetfacile.domain.Ride;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.exception.RideIsFullBusinessException;
import com.poe.trajetfacile.repository.BookingRepository;
import com.poe.trajetfacile.repository.RideRepository;
import com.poe.trajetfacile.repository.UserRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;

@Service
@Transactional
public class BookingService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BookingRepository bookingRepository;

    public Booking bookARide(Long userId, Long rideId) throws RideIsFullBusinessException {
        System.out.println("booking!!!");
        Booking booking = null;
        User user = userRepository.findOne(userId);
        Ride ride = rideRepository.findOne(rideId);

        if (ride.getSeats() > 0) {
            ride.setSeats((short) (ride.getSeats() - 1));

            booking = new Booking();
            booking.setUser(user);
            booking.setRide(ride);

            user.getBookings().add(booking);
            ride.getBookings().add(booking);

            bookingRepository.save(booking);

        } else {
            throw new RideIsFullBusinessException("plus de places");
        }
        System.out.println(booking.getRide().getId());
        return booking;
    }

    /**
     * Permet d'annuler une réservation
     */
    public void cancel(long bookingId) throws Exception {
        Booking booking = bookingRepository.findOne(bookingId);

        Instant instant = booking.getRide().getStartDate().toInstant();
        LocalDateTime rideStartDate = LocalDateTime.ofInstant(instant, ZoneId.systemDefault());

        if (BookingDelegateService.isCancelable(LocalDateTime.now(), rideStartDate)) {
            booking.getRide().setSeats((short) (booking.getRide().getSeats() + 1));
            rideRepository.save(booking.getRide());
            bookingRepository.delete(booking);
        } else {
            throw new Exception("impossible d'annuler le trajet");
        }
    }

    public List<Booking> findAllForUser(long userId) {
        User user = userRepository.findOne(userId);
        Hibernate.initialize(user.getBookings());
        return user.getBookings();
    }

}
