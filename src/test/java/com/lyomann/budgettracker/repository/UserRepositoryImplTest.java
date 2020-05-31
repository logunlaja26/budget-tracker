package com.lyomann.budgettracker.repository;


import com.lyomann.budgettracker.document.BudgetAllowance;
import com.lyomann.budgettracker.document.User;
import com.lyomann.budgettracker.exception.UserRegistrationException;
import com.sun.org.apache.bcel.internal.generic.NEW;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static com.lyomann.budgettracker.Constants.USERS_COLLECTION;
import static com.lyomann.budgettracker.Constants.findByUsernameQuery;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserRepositoryImplTest {

    @Autowired
    MongoTemplate mongoTemplate;

    private UserRepository userRepository;

    private static final String USERNAME = "bob123";
    private static final String NEW_USERNAME = "sam55";
    private static final List<BudgetAllowance> budgetAllowances = getBudgetAllowances();
    private User bob123;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(mongoTemplate);

        mongoTemplate.dropCollection(USERS_COLLECTION);

        bob123 = mongoTemplate.insert(User.builder().username(USERNAME).budgetAllowances(budgetAllowances).build(), USERS_COLLECTION);
    }

    @Test
    void createUser_canSaveNewUsers() {
        List<BudgetAllowance> budgetAllowances = Arrays.asList(BudgetAllowance.builder()
                        .category("Groceries")
                        .maxThreshold(500)
                        .build(),
                BudgetAllowance.builder()
                        .category("Shopping")
                        .maxThreshold(250)
                        .build());
        userRepository.createUser(User.builder().username(NEW_USERNAME).budgetAllowances(budgetAllowances).build());

        User updatedUser = mongoTemplate.findOne(findByUsernameQuery(NEW_USERNAME), User.class);
        assertThat(updatedUser.getId()).isNotNull();
        assertThat(updatedUser.getBudgetAllowances().size()).isEqualTo(2);
    }

    @Test
    void createUser_throwsUserRegistrationException_whenUsernameAlreadyExistsInDatabase() {
        UserRegistrationException ex = assertThrows(UserRegistrationException.class,
                () -> userRepository.createUser(bob123));

        assertThat(ex.getMessage()).isEqualTo("The username bob123 is already taken");
    }

    @Test
    void getUser() {

    }

    private static List<BudgetAllowance> getBudgetAllowances() {
        return Arrays.asList(
                BudgetAllowance.builder()
                        .category("Groceries")
                        .maxThreshold(300)
                        .build(),
                BudgetAllowance.builder()
                        .category("Shopping")
                        .maxThreshold(500)
                        .build(),
                BudgetAllowance.builder()
                        .category("Entertainment")
                        .maxThreshold(200)
                        .build(),
                BudgetAllowance.builder()
                        .category("Outside Food")
                        .maxThreshold(125)
                        .build(),
                BudgetAllowance.builder()
                        .category("Mortgage")
                        .maxThreshold(2200)
                        .build(),
                BudgetAllowance.builder()
                        .category("Utilities")
                        .maxThreshold(200)
                        .build()
        );
    }
}
