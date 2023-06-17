package com.logicea.cardsrestapi.user.service;

import com.logicea.cardsrestapi.user.model.Role;
import com.logicea.cardsrestapi.user.model.User;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;


@Component
@Slf4j
public class UserSeeder implements CommandLineRunner {
    @Autowired
    UserDetailsManager userDetailsManager;

    @Override
    public void run(String... args) {
        User user = new User.UserBuilder("john@logicea.com", "password")
                .userRole(Role.ADMIN)
                .build();
        userDetailsManager.createUser(user);

        User user2 = new User.UserBuilder("jack@logicea.com", "password")
                .userRole(Role.MEMBER)
                .build();
        userDetailsManager.createUser(user2);
    }
}
