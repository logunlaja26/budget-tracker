package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.document.User;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.repository.MongoRepository;


import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.*;
import static org.springframework.data.mongodb.core.query.Query.*;

public class UserRepositoryImpl implements UserRepository {

    private MongoTemplate mongoTemplate;

    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createUser(User user) {

    }

    @Override
    public void updateExpenses(String username, Expense expense) {

    }

    @Override
    public Optional<User> findUserByUsername(String username) {
        User user = mongoTemplate.findOne(query(where("username").is(username)), User.class);
        return Optional.ofNullable(user);
    }

    @Override
    public void deleteExpense(String username, long expenseId) {

    }
}

