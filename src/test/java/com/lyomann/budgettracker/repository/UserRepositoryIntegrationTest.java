package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.document.User;
import com.mongodb.DBObject;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.lyomann.budgettracker.document.Category.*;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserRepositoryIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    private UserRepository userRepository;



    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(mongoTemplate);
    }

    @Test
    void findUserByUsername_retrievesUserFromTheDatabase() {


    }

    @Test
    void createNewUserInDatabase() {
        userRepository.createUser("Freddie");
        Assertions.assertThat(mongoTemplate.findAll(DBObject.class, "users")).isNotEmpty();
    }

    @Test
    void findUserByUsername_andUpdateListOfExpenses() {

    }
}