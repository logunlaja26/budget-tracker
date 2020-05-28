package com.lyomann.budgettracker.service;

import com.lyomann.budgettracker.dto.ExpenseDto;
import com.lyomann.budgettracker.dto.ExpenseListDto;

import java.time.Month;

public interface ExpenseService {
    void saveExpense(String username, ExpenseDto expenseDto);
    ExpenseListDto getExpenseHistory(String username);
    ExpenseListDto getMonthlyExpensesByCategory(String username, String category, Month month);
    void removeExpense(String username, String expenseId);
}
