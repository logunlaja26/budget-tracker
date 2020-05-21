package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.document.User;

import java.util.Optional;

public interface UserRepository {
    void createUser(User user);
    void updateExpenses(String username, Expense expense);
    Optional<User> findUserByUsername(String username);
    void deleteExpense(String username, long expenseId);

}
