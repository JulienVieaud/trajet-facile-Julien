package com.poe.trajetfacile.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public void batchInsert() {
        List<String> users = new ArrayList<>();
        List<Object[]> parameters = new ArrayList<Object[]>();
        for (int i = 0; i < 10000; i++) {
            parameters.add(new String[]{"login " + i});
        }
        jdbcTemplate.batchUpdate("INSERT INTO user(login) VALUES (?)", parameters);
    }

}
