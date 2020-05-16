package com.lyomann.budgettracker.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Entry {
    private String entryId = ObjectId.get().toString();
    private LocalDate date;
    private double amount;
    private EntryType type;
    private Category category;
    private String description;
}
