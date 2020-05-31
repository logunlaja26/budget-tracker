package com.lyomann.budgettracker.feature;

import com.lyomann.budgettracker.document.Category;
import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static com.lyomann.budgettracker.Constants.EXPENSES_COLLECTION;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureDataMongo
public class BudgetTrackerFeatureTest {

    @Autowired
    private WebApplicationContext context;

    @Autowired
    private ExpenseRepository expenseRepository;

    @Autowired
    MongoTemplate mongoTemplate;

    private MockMvc mockMvc;

    private static final String USERNAME = "sdiamante13";


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
        resetDatabase();
        initializeDatabase();
    }

    @Test
    void getAllExpenses_returns200OK() throws Exception {
        mockMvc.perform(get("/api/v1/users/{username}/expenses", USERNAME))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.expenses", hasSize(2)))
                .andExpect(jsonPath("$.expenses[0].description", is("Drinks with the boys")))
                .andExpect(jsonPath("$.expenses[1].transactionDate", is("5/21/2020")));
    }

    private void resetDatabase() {
        mongoTemplate.dropCollection(EXPENSES_COLLECTION);
    }

    private void initializeDatabase() {
        Expense expense1 = Expense.builder()
                .username(USERNAME)
                .category(Category.OTHER)
                .amount(65.50)
                .description("Drinks with the boys")
                .transactionDate(LocalDate.of(2020, 5, 28))
                .build();

        Expense expense2 = Expense.builder()
                .username(USERNAME)
                .category(Category.SHOPPING)
                .amount(127.88)
                .description("New clothes")
                .transactionDate(LocalDate.of(2020, 5, 21))
                .build();

        expenseRepository.addExpense(expense1);
        expenseRepository.addExpense(expense2);
    }
}
