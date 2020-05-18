package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.document.User;
import com.mongodb.BasicDBObject;
import com.okta.commons.lang.Assert;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Optional;

import static com.lyomann.budgettracker.Constants.*;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void createUser(User user) {
        Assert.isTrue(user.getExpenses().size() < 2, NEW_USER_EXPENSES_ERROR_MESSAGE);
        user.getExpenses()
                .stream()
                .findFirst()
                .ifPresent(entry -> entry.setExpenseId(1L));
        mongoTemplate.insert(user);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(mongoTemplate.findOne(findByUsernameQuery(username), User.class));
    }

    @Override
    public void updateExpenses(String username, Expense expense) {
        Query query = findByUsernameQuery(username);
        User userInDatabase = mongoTemplate.findOne(query, User.class);
        Assert.notNull(userInDatabase, UPDATE_EXPENSES_ERROR_MESSAGE);

        expense.setExpenseId((long) (userInDatabase.getExpenses().size() + 1));
        Update update = new Update().addToSet("expenses", expense);
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void deleteExpense(String username, long expenseId) {
        Query query = findByUsernameQuery(username);
        Assert.isTrue(mongoTemplate.exists(query, User.class), USER_DOES_NOT_EXIST_ERROR_MESSAGE);
        Update update = new Update().pull("expenses", new BasicDBObject("expenseId", expenseId));
        mongoTemplate.updateMulti(findByUsernameQuery(username), update, User.class);
    }

}
