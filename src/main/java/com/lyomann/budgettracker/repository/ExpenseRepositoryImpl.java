package com.lyomann.budgettracker.repository;

import com.lyomann.budgettracker.document.Category;
import com.lyomann.budgettracker.document.Expense;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.Month;
import java.util.List;

import static com.lyomann.budgettracker.Constants.*;
import static org.springframework.data.mongodb.core.query.Criteria.where;

@Repository
@RequiredArgsConstructor
public class ExpenseRepositoryImpl implements ExpenseRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public Expense addExpense(Expense expense) {
        return mongoTemplate.insert(expense, EXPENSES_COLLECTION);
    }

    @Override
    public List<Expense> findAllExpensesByUsername(String username) {
        return mongoTemplate.find(findByUsernameQuery(username), Expense.class);
    }

    @Override
    public Expense updateExpense(Expense expense) {
        return mongoTemplate.save(expense, EXPENSES_COLLECTION);
    }

    @Override
    public void deleteExpense(String username, String expenseId) {
        Query usernameWithExpenseIdQuery = findByExpenseIdQuery(expenseId).addCriteria(where("username").is(username));
        mongoTemplate.remove(usernameWithExpenseIdQuery, Expense.class);
    }

    @Override
    public List<Expense> getExpensesByCategoryAndMonth(String username, Category category, Month month) {
        LocalDate beginningOfTheMonth = LocalDate.of(LocalDate.now().getYear(), month, 1);
        Query query = findByUsernameQuery(username)
                .addCriteria(where("category").is(category))
                .addCriteria(where("transactionDate").gte(beginningOfTheMonth));
        return mongoTemplate.find(query, Expense.class);
    }
}