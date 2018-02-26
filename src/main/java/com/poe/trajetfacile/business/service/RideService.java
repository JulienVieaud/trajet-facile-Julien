package com.poe.trajetfacile.business.service;

import com.poe.trajetfacile.aop.Chrono;
import com.poe.trajetfacile.domain.Ride;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.repository.RideRepository;
import com.poe.trajetfacile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;

@Service
@Transactional
public class RideService {

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SimpMessagingTemplate template;

    @Chrono
    public Ride offerARide(Date startDate, String fromCity, String toCity, Double cost, Short seats, Long userWhoProposed) {

        // TODO ...contrôles métiers

        Ride ride = new Ride();
        ride.setStartDate(startDate);
        ride.setFromCity(fromCity);
        ride.setToCity(toCity);
        ride.setCost(cost);
        ride.setSeats(seats);

        User user = userRepository.findOne(userWhoProposed);
        ride.setUserWhoProposed(user);
        rideRepository.save(ride);

        System.out.println("sending event");
        template.convertAndSend("/topic/newRide", ride);
        return ride;
    }

}
