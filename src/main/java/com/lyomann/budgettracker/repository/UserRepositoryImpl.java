package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.Constants;
import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.document.User;
import com.lyomann.budgettracker.exception.UserRegistrationException;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import java.util.Collections;
import java.util.Optional;

import static com.lyomann.budgettracker.Constants.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void createUser(String username) {
        if (mongoTemplate.exists(Constants.findByUsernameQuery(username), User.class)) {
            throw new UserRegistrationException("The username " + username + " is already taken");
        }

        mongoTemplate.insert(User.builder()
                        .username(username)
                        .expenseIds(Collections.emptyList())
                        .build(),
                USERS_COLLECTION);
    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        return Optional.ofNullable(mongoTemplate.findOne(findByUsernameQuery(username), User.class));
    }

    @Override
    public void updateExpenses(String username, Expense expense) {
        Expense savedExpense = mongoTemplate.insert(expense, EXPENSES_COLLECTION);

        Query query = findByUsernameQuery(username);
        Update update = new Update().addToSet("expenseIds", savedExpense.getExpenseId());
        mongoTemplate.updateFirst(query, update, User.class);
    }

    @Override
    public void deleteExpense(String username, ObjectId expenseId) {
        Query expenseQuery = query(where("expenseId").is(expenseId));
        mongoTemplate.remove(expenseQuery, Expense.class);
        Update update = new Update().pull("expenseIds", expenseId);
        mongoTemplate.updateMulti(findByUsernameQuery(username), update, User.class);
    }
}
