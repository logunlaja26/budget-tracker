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
    String expenseId;
    Category category;
    double amount;
    String description;
    String transactionDate;
}
