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


    User user = User.builder().username("Ly").build();

    Expense expense1 = new Expense(100000L, LocalDate.of(2017, 11, 6),26.00,RENT,"Rent pay");
    Expense expense2 = new Expense(100001L, LocalDate.of(2017, 11, 7),29.00,OTHER,"Entertainment");
    List<Expense> expenseList = Arrays.asList(expense1, expense2);

    User user1 = User.builder().id("1234").expenses(expenseList).username("Ty").build();

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(mongoTemplate);

        mongoTemplate.insert(user, "users");
        mongoTemplate.insert(user1, "users");


    }

    @Test
    void findUserByUsername_retrievesUserFromTheDatabase() {
        mongoTemplate.insert(user, "users");
        Optional<User> ly = userRepository.findUserByUsername("Ly");
        Assertions.assertThat(ly.isPresent()).isTrue();
        Assertions.assertThat(ly.get()).isEqualTo(user);


    }

    @Test
    void createNewUserInDatabase() {
        mongoTemplate.insert(user1, "users");
        Assertions.assertThat(mongoTemplate.findAll(DBObject.class, "users")).isNotEmpty();
    }

    @Test
    void findUserByUsername_andUpdateListOfExpenses() {
        Expense expense3 = new Expense(100002L, LocalDate.of(2017, 11, 9),69.00,SHOPPING,"Appliances");
        userRepository.updateExpenses("Ty", expense3);
        Assertions.assertThat(mongoTemplate.findAll(DBObject.class, "users")).isNotEmpty();
    }
}