package com.lyomann.budgettracker.service;

import com.lyomann.budgettracker.dto.BudgetAllowanceDto;
import com.lyomann.budgettracker.dto.UserDto;

public interface UserService {
    void createUser(UserDto userDto);
    UserDto getUser(String username);
    void updateBudgetAllowance(String username, String category, BudgetAllowanceDto updatedBudgetAllowanceDto);
    void addBudgetAllowance(String username, BudgetAllowanceDto budgetAllowance);
}
