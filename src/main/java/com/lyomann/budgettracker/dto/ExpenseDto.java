package com.lyomann.budgettracker.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ExpenseDto {
    String expenseId;
    @NotNull(message = "Category is required")
    String category;
    @NotNull(message = "Amount is required")
    double amount;
    String description;
    @NotNull(message = "Transaction Date is required")
    String transactionDate;
}
