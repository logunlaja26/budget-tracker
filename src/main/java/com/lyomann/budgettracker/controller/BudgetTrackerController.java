package com.lyomann.budgettracker.controller;


import com.lyomann.budgettracker.dto.ExpenseDto;
import com.lyomann.budgettracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController


public class BudgetTrackerController {

    private ExpenseService expenseService;

    public BudgetTrackerController(ExpenseService expenseService) {
        this.expenseService = expenseService;
    }

    @PostMapping("/users/{username}/expenses")
    void addExpense(@PathVariable("username") String username,
                                          @Valid @RequestBody ExpenseDto expenseDto) throws Exception{
        expenseService.saveExpense(username, expenseDto);
        System.out.println("Expense was saved");

    }

}
