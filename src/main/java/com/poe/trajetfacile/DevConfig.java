package com.poe.trajetfacile;

import com.poe.trajetfacile.business.service.UserService;
import com.poe.trajetfacile.domain.User;
import com.poe.trajetfacile.jdbc.UserDao;
import com.poe.trajetfacile.repository.RideRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("dev")
public class DevConfig {

    @Autowired
    UserService userService;

    @Autowired
    RideRepository rideRepository;

    @Autowired
    UserDao userDao;

    @Bean
    public InitializingBean init() {
        return () -> {
            System.out.println("init");
            User user = new User();
            user.setLogin("jean");
            user.setPassword("jean");
            userService.signup(user);

            user = new User();
            user.setLogin("marc");
            user.setPassword("marc");
            userService.signup(user);

            userDao.batchInsert();

        };
    }
}
