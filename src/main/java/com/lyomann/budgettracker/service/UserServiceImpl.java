package com.lyomann.budgettracker.service;

import com.lyomann.budgettracker.document.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MongoTemplate mongoTemplate;

    @Override
    public void updateUserEntries(User user) {
        Query query = query((where("username").is(user.getUsername())));
        if (mongoTemplate.exists(query, User.class)) {
            Update update = new Update();
            update.addToSet("entries", user.getEntries().get(0));
            mongoTemplate.upsert(query, update, User.class);
        } else {
            mongoTemplate.insert(user);
        }
    }

    @Override
    public Optional<User> fetchUser(String username) {
        return Optional.of(mongoTemplate.findOne(query(where("username").is(username)), User.class));
    }

    @Override
    public void deleteEntry(String username, String entryId) { }
}
