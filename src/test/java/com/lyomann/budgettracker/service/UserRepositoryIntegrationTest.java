package com.lyomann.budgettracker.service;

import com.lyomann.budgettracker.document.Category;
import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.document.User;
import com.lyomann.budgettracker.repository.UserRepository;
import com.lyomann.budgettracker.repository.UserRepositoryImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.Optional;

import static com.lyomann.budgettracker.Constants.*;
import static com.lyomann.budgettracker.document.Category.*;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@DataMongoTest
class UserRepositoryIntegrationTest {

    @Autowired
    private MongoTemplate mongoTemplate;

    private UserRepository userRepository;

    private static final String USERNAME = "bob123";
    private static final String NEW_USERNAME = "sam55";

    private User bob123;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(mongoTemplate);

        mongoTemplate.dropCollection(USERS_COLLECTION);

        mongoTemplate.insert(createUserBuilder().expenses(Arrays.asList(
                createExpense(1L, 33.99, SHOPPING, "Table"),
                createExpense(2L, 112.99, ENTERTAINMENT, "Bowling"))).build(),
                USERS_COLLECTION);
        bob123 = mongoTemplate.findOne(findByUsernameQuery(USERNAME), User.class);
    }

    @Test
    void createUser_canSaveNewUsersWithNoInitialExpenses() {
        userRepository.createUser(User.builder().username(NEW_USERNAME).build());

        User updatedUser = getThisUser(NEW_USERNAME);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getExpenses().size()).isEqualTo(0);
    }

    @Test
    void createUser_canSaveNewUsersWithOneInitialExpense() {
        userRepository.createUser(User.builder().username(NEW_USERNAME).expenses(singletonList(
                createExpense(null, 45.99, UTILITIES, "Electric")))
                .build());

        User updatedUser = getThisUser(NEW_USERNAME);
        assertThat(updatedUser).isNotNull();
        assertThat(updatedUser.getExpenses().size()).isEqualTo(1);
        assertThat(updatedUser.getExpenses().get(0).getExpenseId()).isEqualTo(1L);
    }

    @Test
    void createUser_throwsIllegalArgumentException_whenAttemptingToSaveNewUsersWithMoreThanOneInitialExpense() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                userRepository.createUser(User.builder().username(NEW_USERNAME).expenses(Arrays.asList(
                        createExpense(null, 45.99, UTILITIES, "Electric"),
                        createExpense(null, 2000.00, MORTGAGE, "")))
                        .build()));

        assertThat(ex.getMessage()).isEqualTo(NEW_USER_EXPENSES_ERROR_MESSAGE);
    }

    @Test
    void findUserByUsername_retrievesTheUserFromTheDatabase() {
        Optional<User> user = userRepository.findUserByUsername(USERNAME);

        assertThat(user.isPresent()).isTrue();
        assertThat(user.get().getExpenses().size()).isEqualTo(2);
    }

    @Test
    void updateExpenses_addsAnExpenseToTheUsersExpensesList() {
        userRepository.updateExpenses(USERNAME, createExpense(null, 10.11, SHOPPING, "Gas"));
        User updatedUser = getThisUser(USERNAME);
        assertThat(updatedUser.getExpenses().size()).isEqualTo(3);
    }

    @Test
    void updateExpenses_throwsIllegalArgumentException_whenUserDoesNotExistInTheDatabase() {
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () ->
                userRepository.updateExpenses(NEW_USERNAME, createExpense(null, 10.11, SHOPPING, "Gas")));

        assertThat(ex.getMessage()).isEqualTo(UPDATE_EXPENSES_ERROR_MESSAGE);
    }

    @Test
    void deleteEntry_removesOneEntryFromGivenUser() {
        long expenseId = bob123.getExpenses().get(0).getExpenseId();
        userRepository.deleteExpense(USERNAME, expenseId);

        User updatedUser = getThisUser(USERNAME);
        assertThat(updatedUser.getExpenses().size()).isEqualTo(1);
        assertThat(updatedUser.getExpenses().get(0).getCategory()).isEqualTo(ENTERTAINMENT);
    }

    @Test
    void deleteEntry_throwsIllegalArgumentException_whenUserDoesNotExistInTheDatabase() {
        long entryId = bob123.getExpenses().get(0).getExpenseId();
        IllegalArgumentException ex = assertThrows(IllegalArgumentException.class, () -> userRepository.deleteExpense(NEW_USERNAME, entryId));

        assertThat(ex.getMessage()).isEqualTo(USER_DOES_NOT_EXIST_ERROR_MESSAGE);
    }

    private User getThisUser(String username) {
        return mongoTemplate.findOne(findByUsernameQuery(username), User.class);
    }

    private User.UserBuilder createUserBuilder() {
        return User.builder()
                .username(USERNAME);
    }

    private Expense createExpense(Long id, double amount, Category category, String description) {
        return Expense.builder()
                .expenseId(id)
                .amount(amount)
                .category(category)
                .description(description)
                .build();
    }
}