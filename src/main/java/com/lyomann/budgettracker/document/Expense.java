package com.lyomann.budgettracker.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Expense {
    @Indexed(unique = true)
    private Long expenseId;
    private LocalDate transactionDate = LocalDate.now();
    private double amount;
    private Category category;
    private String description;
}