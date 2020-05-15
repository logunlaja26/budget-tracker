package com.lyomann.budgettracker.document;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@NoArgsConstructor
@AllArgsConstructor
@Document
@Getter
@Setter
public class Entry {

    @Id
    private Integer id;
    private String date;
    private Integer amount;
    private String  type;

    enum Record {
        INCOME,
        EXPENSE,
    }

    private String category;
    private String description;

    enum ExpenseType {
        GROCERIES,
        ENTERTAINMENT,
        UTILITIES,
        PAYCHECK,
        RENT,
        MORTGAGE,
        SHOPPING,
        OTHER,

    }
}
