package com.poe.trajetfacile.business.service;

import com.poe.trajetfacile.domain.Ride;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.repository.RideRepository;
import com.poe.trajetfacile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;

/**
 * Business Code about user management.
 */
@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private InMemoryUserDetailsManager inMemoryUserDetailsManager;

    public void signup(User user) {
        userRepository.save(user);
        try {
            inMemoryUserDetailsManager.createUser(new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), new ArrayList<GrantedAuthority>()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void addRide(Long userId, Long rideId) {
        User user = userRepository.findOne(userId);
        Ride ride = rideRepository.findOne(rideId);
        user.getProposedRides().add(ride);
        ride.setUserWhoProposed(user);
        userRepository.save(user);
    }
}
