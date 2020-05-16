package com.lyomann.budgettracker.service;

import com.lyomann.budgettracker.document.User;

import java.util.Optional;


public interface UserService {

    //    create entries for user
//    retrieve all entries per user (add pagination in the future)
//    updating an entry for a particular user
//    delete an entry

    void updateUserEntries(User user); // should support save and update
    Optional<User> fetchUser(String username);
    void deleteEntry(String username, String entryId);
}
