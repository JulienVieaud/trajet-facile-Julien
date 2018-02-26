package com.poe.trajetfacile.repository;

import com.poe.trajetfacile.domain.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

    public User findByLogin(String login);

    public User findByBookingsIs(long bookingId);
}
