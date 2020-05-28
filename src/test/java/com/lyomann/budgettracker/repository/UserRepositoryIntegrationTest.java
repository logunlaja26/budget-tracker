package com.lyomann.budgettracker.repository;


import com.lyomann.budgettracker.document.User;
import com.lyomann.budgettracker.exception.UserRegistrationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.lyomann.budgettracker.Constants.USERS_COLLECTION;
import static com.lyomann.budgettracker.Constants.findByUsernameQuery;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataMongoTest
public class UserRepositoryIntegrationTest {

    @Autowired
    MongoTemplate mongoTemplate;

    private UserRepository userRepository;

    private static final String USERNAME = "bob123";
    private static final String NEW_USERNAME = "sam55";

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(mongoTemplate);

        mongoTemplate.dropCollection(USERS_COLLECTION);

        mongoTemplate.insert(User.builder().username(USERNAME).build(),
                USERS_COLLECTION);
    }

    @Test
    void createUser_canSaveNewUsers() {
        userRepository.createUser(NEW_USERNAME);

        User updatedUser = mongoTemplate.findOne(findByUsernameQuery(NEW_USERNAME), User.class);
        assertThat(updatedUser.getId()).isNotNull();
    }

    @Test
    void createUser_throwsUserRegistrationException_whenUsernameAlreadyExistsInDatabase() {
        UserRegistrationException ex = assertThrows(UserRegistrationException.class,
                () -> userRepository.createUser(USERNAME));

        assertThat(ex.getMessage()).isEqualTo("The username bob123 is already taken");
    }
}
