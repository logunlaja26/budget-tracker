package com.lyomann.budgettracker.controller;

import com.lyomann.budgettracker.dto.BudgetOverview;
import com.lyomann.budgettracker.dto.ExpenseDto;
import com.lyomann.budgettracker.dto.ExpenseListDto;
import com.lyomann.budgettracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class BudgetTrackerController {

    private final ExpenseService expenseService;

    @GetMapping("/{username}/expenses")
    public ExpenseListDto getAllExpenses(@PathVariable String username) {
        return expenseService.getExpenseHistory(username);
    }

//    @GetMapping("/{username}/budget/overview")
//    public BudgetOverview getBudgetOverview(@PathVariable String username) {
//        return expenseService.getBudgetOverview(username);
//    }


    @PostMapping("/{username}/expenses")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void addExpense(@PathVariable String username, @RequestBody ExpenseDto expenseDto) {
        expenseService.saveExpense(username, expenseDto);
    }
}
