
package com.poe.trajetfacile.api;

import com.poe.trajetfacile.business.service.UserService;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.repository.RideRepository;
import com.poe.trajetfacile.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    UserService userService;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RideRepository rideRepository;


    @GetMapping("{id}")
    public User find(@PathVariable("id") Long accountId) {
        return userRepository.findOne(accountId);
    }

    @GetMapping
    public Iterable<User> findAll() {
        return userRepository.findAll();
    }

    @PostMapping
    public void save(@RequestBody User user) {
        System.out.println("signup | user: " + user);
        userService.signup(user);
    }

    @PostMapping("{userId}/{rideId}")
    public void addRide(@PathVariable("userId") Long userId, @PathVariable("rideId") Long rideId) {
        userService.addRide(userId, rideId);
    }
}