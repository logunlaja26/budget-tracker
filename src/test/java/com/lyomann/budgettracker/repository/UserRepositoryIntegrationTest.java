package com.lyomann.budgettracker.repository;


import com.lyomann.budgettracker.document.Category;
import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.document.User;
import com.lyomann.budgettracker.exception.UserRegistrationException;
import org.bson.types.ObjectId;
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

import static com.lyomann.budgettracker.Constants.*;
import static com.lyomann.budgettracker.document.Category.*;
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

    private final LocalDate todaysDate = LocalDate.of(2020, 5, 24);

    private User bob123;
    private Expense expense1, expense2, expense3, expense4, expense5;

    @BeforeEach
    void setUp() {
        userRepository = new UserRepositoryImpl(mongoTemplate);

        resetData();

        initializeData();
    }

    @Test
    void createUser_canSaveNewUsersWithNoInitialExpenses() {
        userRepository.createUser(NEW_USERNAME);

        User updatedUser = getThisUser(NEW_USERNAME);
        assertThat(updatedUser.getId()).isNotNull();
        assertThat(updatedUser.getExpenseIds().size()).isEqualTo(0);
    }

    @Test
    void createUser_throwsUserRegistrationException_whenUsernameAlreadyExistsInDatabase() {
        UserRegistrationException ex = assertThrows(UserRegistrationException.class,
                () -> userRepository.createUser(USERNAME));

        assertThat(ex.getMessage()).isEqualTo("The username bob123 is already taken");
    }

    @Test
    void findByUsername_retrievesTheUserFromTheDatabase() {
        Optional<User> actualUser = userRepository.findUserByUsername(USERNAME);

        assertThat(actualUser.isPresent()).isTrue();
        assertThat(actualUser.get().getExpenseIds().size()).isEqualTo(5);
    }

    @Test
    void findUserByUsername_returnsOptionalEmptyWhenUserDoesNotExistInTheDatabase() {
        Optional<User> user = userRepository.findUserByUsername(NEW_USERNAME);

        assertThat(user.isPresent()).isFalse();
    }

    @Test
    void updateExpenses_addsAnExpenseToTheExpensesCollection_andTheExpenseIdToTheUsersCollection() {
        userRepository.updateExpenses(USERNAME, createExpense(todaysDate, 10.11, SHOPPING, "Gas"));
        User updatedUser = getThisUser(USERNAME);
        assertThat(updatedUser.getExpenseIds().size()).isEqualTo(6);
    }

    @Test
    void deleteExpense_removesOneExpenseFromGivenUser() {
        ObjectId expenseId = bob123.getExpenseIds().get(0);

        userRepository.deleteExpense(USERNAME, expenseId);

        User updatedUser = getThisUser(USERNAME);
        List<Expense> expenses = mongoTemplate.findAll(Expense.class);

        assertThat(updatedUser.getExpenseIds().size()).isEqualTo(4);
        assertThat(expenses.size()).isEqualTo(4);
        assertThat(expenses.get(0).getCategory()).isEqualTo(UTILITIES);
    }

    private User getThisUser(String username) {
        return mongoTemplate.findOne(findByUsernameQuery(username), User.class);
    }

    private User.UserBuilder createUserBuilder() {
        return User.builder().username(USERNAME);
    }

    private Expense createExpense(LocalDate transactionDate, double amount, Category category, String description) {
        return Expense.builder()
                .amount(amount)
                .transactionDate(transactionDate)
                .category(category)
                .description(description)
                .build();
    }

    private void resetData() {
        mongoTemplate.dropCollection(USERS_COLLECTION);
        mongoTemplate.dropCollection(EXPENSES_COLLECTION);
    }

    private void initializeData() {
        expense1 = mongoTemplate.insert(createExpense(todaysDate, 33.99, SHOPPING, "Table"), EXPENSES_COLLECTION);
        expense2 = mongoTemplate.insert(createExpense(todaysDate, 122.76, UTILITIES, "Water Bill"), EXPENSES_COLLECTION);
        expense3 = mongoTemplate.insert(createExpense(LocalDate.of(2020, 5, 23), 112.99, ENTERTAINMENT, "Bowling"), EXPENSES_COLLECTION);
        expense4 = mongoTemplate.insert(createExpense(LocalDate.of(2020, 5, 1), 120.00, GROCERIES, ""), EXPENSES_COLLECTION);
        expense5 = mongoTemplate.insert(createExpense(LocalDate.of(2020, 4, 15), 76.99, UTILITIES, "Electric Bill"), EXPENSES_COLLECTION);

        bob123 = mongoTemplate.insert(createUserBuilder().expenseIds(
                Arrays.asList(
                        expense1.getExpenseId(),
                        expense2.getExpenseId(),
                        expense3.getExpenseId(),
                        expense4.getExpenseId(),
                        expense5.getExpenseId()
                )).build(),
                USERS_COLLECTION);
    }
}
