package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.Constants;
import com.lyomann.budgettracker.document.User;
import com.lyomann.budgettracker.exception.UserRegistrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Repository;

import static com.lyomann.budgettracker.Constants.USERS_COLLECTION;

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
                        .build(),
                USERS_COLLECTION);
    }
}
