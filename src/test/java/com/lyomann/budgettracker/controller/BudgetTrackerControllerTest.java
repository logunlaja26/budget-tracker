package com.lyomann.budgettracker.controller;

import com.lyomann.budgettracker.service.ExpenseService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class BudgetTrackerControllerTest {

    @Mock
    private ExpenseService mockExpenseService;

    @InjectMocks
    private BudgetTrackerController budgetTrackerController;

    @BeforeEach
    void setUp() {

    }

    @Test
    void name() {

    }
}
