package com.poe.trajetfacile.business.service;

import com.poe.trajetfacile.domain.Booking;
import com.poe.trajetfacile.domain.Ride;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.exception.RideIsFullBusinessException;
import com.poe.trajetfacile.repository.RideRepository;
import com.poe.trajetfacile.repository.UserRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BookingServiceTests {

    @Autowired
    private BookingService bookingService;

    @Autowired
    private UserService userService;

    @Autowired
    private RideService rideService;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Test
    public void canBookARide() throws RideIsFullBusinessException {

        short seat = 1;

        assertThat(rideRepository.count()).isEqualTo(0);

        User user = new User();
        user.setLogin("login");
        user.setPassword("password");
        user.setBirthDate(LocalDate.of(2018, 02, 02));

        // création d'un utilisateur
        userService.signup(user);
        assertThat(userRepository.findOne(user.getId())).isNotNull();

        // création d'un trajet
        Ride ride = rideService.offerARide(new Date(), "Angers", "Nantes", 3d, seat, user.getId());
        assertThat(rideRepository.findOne(ride.getId())).isNotNull();

        // on vérifie que l'utilisateur n'a pas de réservation
        List<Booking> bookings = bookingService.findAllForUser(user.getId());
        assertThat(bookings.size()).isEqualTo(0);

        // effectue une réservation
        Booking booking = bookingService.bookARide(user.getId(), ride.getId());

        // on vérifie que l'utilisateur a désormais une réservation
        bookings = bookingService.findAllForUser(user.getId());
        assertThat(bookings.size()).isGreaterThan(0); // pour l'exemple
        assertThat(bookings.size()).isEqualTo(1);

        assertThat(rideRepository.findOne(ride.getId()).getSeats()).isEqualTo((short) (seat - 1));

    }
}
