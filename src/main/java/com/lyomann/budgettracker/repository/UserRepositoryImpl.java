package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.User;
import org.springframework.data.mongodb.core.MongoTemplate;


public class UserRepositoryImpl implements UserRepository {

    private MongoTemplate mongoTemplate;

    public UserRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public void createUser(String username) {
        User userObject = new User();
        userObject.setUsername(username);
        mongoTemplate.save(userObject);
    }
}

