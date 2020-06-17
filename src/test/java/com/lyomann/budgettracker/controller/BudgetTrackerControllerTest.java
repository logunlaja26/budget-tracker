package com.lyomann.budgettracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.dto.ExpenseDto;
import com.lyomann.budgettracker.service.ExpenseService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Month;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.is;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = BudgetTrackerController.class)
public class BudgetTrackerControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ExpenseService mockExpenseService;

    @Test
    void whenValidInput_thenReturns200() throws Exception {
        ExpenseDto expenseDto = ExpenseDto.builder().category("Shopping").amount(20.00)
                .transactionDate("02/14/2020")
                .description("random shopping").build();

        mockMvc.perform(post("/users/Ly/expenses")
                .content(objectMapper.writeValueAsString(expenseDto))
                .contentType("application/json"))
                .andExpect(status().isOk());

        verify(mockExpenseService).saveExpense("Ly", expenseDto);
    }

    @Test
    void throwsIllegalException() throws Exception {
        ExpenseDto expenseDto = ExpenseDto.builder().amount(20.00)
                .transactionDate("02/14/2020")
                .description("random shopping").build();

        mockMvc.perform(post("/users/Ly/expenses")
                .content(objectMapper.writeValueAsString(expenseDto))
                .contentType("application/json"))
                .andExpect(status().isBadRequest());
    }

    @Test
    void name() {
        List<Expense> expenses = Arrays.asList();
        Mockito.when(mockExpenseService.getMonthlyExpensesByCategory(any(), any(), any()))
                .thenReturn(expenses);
    }
}
