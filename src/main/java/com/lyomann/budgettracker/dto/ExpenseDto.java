package com.lyomann.budgettracker.dto;

import com.lyomann.budgettracker.document.Category;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ExpenseDto {
    private String expenseId;
    private Category category;
    private double amount;
    private String description;
    private String transactionDate;
}
