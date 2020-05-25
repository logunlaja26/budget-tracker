package com.lyomann.budgettracker.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document(collection = "expenses")
public class Expense {
    @Id
    private ObjectId expenseId;
    @Indexed
    private LocalDate transactionDate = LocalDate.now();
    private double amount;
    @Indexed
    private Category category;
    private String description;
}
