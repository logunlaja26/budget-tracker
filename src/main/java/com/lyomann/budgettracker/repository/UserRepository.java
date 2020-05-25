package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.document.User;
import org.bson.types.ObjectId;

import java.util.Optional;


public interface UserRepository {
    void createUser(String username);
    void updateExpenses(String username, Expense expense);
    Optional<User> findUserByUsername(String username);
    void deleteExpense(String username, ObjectId expenseId);
//    List<Expense> getExpensesByCategoryAndMonth(String username, Category category, Month month);
}
