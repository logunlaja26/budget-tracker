package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.document.User;

import java.util.Optional;

public class UserRepositoryImpl  implements UserRepository{

    @Override
    public void createUser(User user) {

    }

    @Override
    public void updateExpenses(String username, Expense expense) {

    }

    @Override
    public Optional findUserByUsername(String username) {
        return Optional.empty();
    }

    @Override
    public void deleteExpense(String username, long expenseId) {

    }
}
