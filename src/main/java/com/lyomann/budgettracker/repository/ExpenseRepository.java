package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.Expense;

import java.time.Month;
import java.util.List;

public interface ExpenseRepository {
    Expense addExpense(Expense expense);
    List<Expense> findAllExpensesByUsername(String username);
    Expense updateExpense(Expense expense);
    void deleteExpense(String expenseId);
    List<Expense> getExpensesByCategoryAndMonth(String username, String category, Month month);
}
