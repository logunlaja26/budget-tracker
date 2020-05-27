package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.Category;
import com.lyomann.budgettracker.document.Expense;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.time.Month;
import java.util.List;

public class ExpenseRespositoryImpl implements ExpenseRepository {

    private MongoTemplate mongoTemplate;

    public ExpenseRespositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public Expense addExpense(Expense expense) {
        return null;
    }

    @Override
    public List<Expense> findAllExpensesByUsername(String username) {
        return null;
    }

    @Override
    public Expense updateExpense(Expense expense) {
        return null;
    }

    @Override
    public void deleteExpense(String expenseId) {

    }

    @Override
    public List<Expense> getExpensesByCategoryAndMonth(String username, Category category, Month month) {
        return null;
    }
}
