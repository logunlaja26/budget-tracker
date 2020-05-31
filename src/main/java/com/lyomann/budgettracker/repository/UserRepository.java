package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.User;

public interface UserRepository {
    User createUser(User user);
    User getUser(String username);
}
