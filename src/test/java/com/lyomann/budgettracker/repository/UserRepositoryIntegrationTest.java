package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.User;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    private UserRepository userRepository;

    User user = User.builder().username("Ly").build();


    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(mongoTemplate);


        mongoTemplate.insert(user, "users");

    }

    @Test
    void findUserByUsername_retrievesUserFromTheDatabase() {
        Optional<User> ly = userRepository.findUserByUsername("Ly");
        Assertions.assertThat(ly.isPresent()).isTrue();
        Assertions.assertThat(ly.get()).isEqualTo(user);


    }
}