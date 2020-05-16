package com.lyomann.budgettracker.service;

import com.lyomann.budgettracker.document.User;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final MongoTemplate mongoTemplate;

    @Override
    public User updateUserEntries(User user) { return null; }

    @Override
    public Optional<User> fetchUser(String username) {
        return Optional.of(mongoTemplate.findOne(query(where("username").is(username)), User.class));
    }

    @Override
    public void deleteEntry(String username, String entryId) { }
}
