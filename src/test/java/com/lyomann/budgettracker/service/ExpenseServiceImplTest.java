package com.lyomann.budgettracker.service;

import com.lyomann.budgettracker.document.Category;
import com.lyomann.budgettracker.document.Expense;
import com.lyomann.budgettracker.dto.ExpenseDto;
import com.lyomann.budgettracker.dto.ExpenseListDto;
import com.lyomann.budgettracker.repository.ExpenseRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;

import java.time.LocalDate;

import static com.lyomann.budgettracker.document.Category.SHOPPING;
import static com.lyomann.budgettracker.document.Category.UTILITIES;
import static java.time.Month.MAY;
import static java.util.Collections.singletonList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ExpenseServiceImplTest {

    @Mock
    private ExpenseRepository mockExpenseRepository;

    private ModelMapper modelMapper = new ModelMapper();

    private ExpenseService expenseService;

    private static final String USERNAME = "username";

    private Expense expense1, expense2, expense3;

    @BeforeEach
    void setUp() {
        expenseService = new ExpenseServiceImpl(mockExpenseRepository, modelMapper);

        createExpenses();
    }

    @Test
    void saveExpense_convertsExpenseDtoToExpense_andSavesToDatabase() {
        ExpenseDto expenseDto = ExpenseDto.builder()
                .category(Category.MORTGAGE)
                .amount(1750.00)
                .description("Monthly mortgage")
                .transactionDate("5/1/2020")
                .build();

        Expense expectedExpense = Expense.builder()
                .username(USERNAME)
                .category(Category.MORTGAGE)
                .amount(1750.00)
                .description("Monthly mortgage")
                .transactionDate(LocalDate.of(2020, 5, 1))
                .build();

        expenseService.saveExpense(USERNAME, expenseDto);

        verify(mockExpenseRepository).addExpense(expectedExpense);
    }

    @Test
    void getExpenseHistory_retrievesExpensesForGivenUsername_convertsEachExpenseToDto_andReturnsAsExpenseListDto() {
        when(mockExpenseRepository.findAllExpensesByUsername(USERNAME))
                .thenReturn(singletonList(expense1));

        ExpenseListDto expenseHistory = expenseService.getExpenseHistory(USERNAME);

        assertThat(expenseHistory.getUsername()).isEqualTo(USERNAME);
        assertThat(expenseHistory.getExpenses().size()).isEqualTo(1);
        assertThat(expenseHistory.getExpenses().get(0).getTransactionDate()).isEqualTo("5/22/2020");
    }

    @Test
    void getMonthlyExpensesByCategory_() {
        when(mockExpenseRepository.getExpensesByCategoryAndMonth(USERNAME, UTILITIES, MAY))
                .thenReturn(singletonList(expense3));

        ExpenseListDto monthlyExpenses = expenseService.getMonthlyExpensesByCategory(USERNAME, UTILITIES, MAY);

        verify(mockExpenseRepository).getExpensesByCategoryAndMonth(USERNAME, UTILITIES, MAY);

        assertThat(monthlyExpenses.getUsername()).isEqualTo(USERNAME);
        assertThat(monthlyExpenses.getExpenses().size()).isEqualTo(1);
        assertThat(monthlyExpenses.getExpenses().get(0).getCategory()).isEqualTo(UTILITIES);
        assertThat(monthlyExpenses.getExpenses().get(0).getTransactionDate()).isEqualTo("5/7/2020");
    }

    @Test
    void removeExpense() {
        expenseService.removeExpense(USERNAME, expense2.getExpenseId());

        verify(mockExpenseRepository).deleteExpense(USERNAME, expense2.getExpenseId());
    }

    private void createExpenses() {
        expense1 = Expense.builder()
                .expenseId("5ed009e7357b394af6606608")
                .username(USERNAME)
                .category(SHOPPING)
                .amount(650.00)
                .transactionDate(LocalDate.of(2020, 5, 22))
                .description("Treadmill")
                .build();

        expense2 = Expense.builder()
                .expenseId("5ed009e7357b394af6606609")
                .username(USERNAME)
                .category(SHOPPING)
                .amount(70.00)
                .transactionDate(LocalDate.of(2020, 4, 29))
                .description("Books")
                .build();

        expense3 = Expense.builder()
                .expenseId("5ed009e7357b394af6606610")
                .username(USERNAME)
                .category(Category.UTILITIES)
                .amount(76.00)
                .transactionDate(LocalDate.of(2020, 5, 7))
                .description("Electric Bill")
                .build();
    }
}