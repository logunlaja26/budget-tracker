package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.BudgetAllowance;
import com.lyomann.budgettracker.document.User;

public interface UserRepository {
    User createUser(User user);
    User getUser(String username);
    void updateBudgetAllowance(String username, String category, BudgetAllowance updatedBudgetAllowance);
    void addBudgetAllowance(String username, BudgetAllowance budgetAllowance);
}
