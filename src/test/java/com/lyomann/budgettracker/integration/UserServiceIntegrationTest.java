package com.lyomann.budgettracker.integration;

import com.lyomann.budgettracker.document.Category;
import com.lyomann.budgettracker.document.Entry;
import com.lyomann.budgettracker.document.EntryType;
import com.lyomann.budgettracker.document.User;
import com.lyomann.budgettracker.service.UserService;
import com.lyomann.budgettracker.service.UserServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.Optional;

import static com.lyomann.budgettracker.document.Category.ENTERTAINMENT;
import static com.lyomann.budgettracker.document.Category.SHOPPING;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class UserServiceIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    private UserService userService;

    private static final String USERNAME = "bob123";

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(mongoTemplate);

        mongoTemplate.insert(createUserBuilder().entries(Arrays.asList(
                createEntry(33.99, SHOPPING, "Table"),
                createEntry(112.99, ENTERTAINMENT, "Bowling"))),
                "users");
    }

    @Test
    void fetchUser() {
        Optional<User> user = userService.fetchUser(USERNAME);

        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getEntries().size()).isEqualTo(2);
    }

    private User.UserBuilder createUserBuilder() {
        return User.builder()
                .username(USERNAME);
    }

    private Entry createEntry(double amount, Category category, String description) {
        return Entry.builder()
                .date(LocalDate.now())
                .amount(amount)
                .category(category)
                .description(description)
                .type(EntryType.EXPENSE)
                .build();
    }
}