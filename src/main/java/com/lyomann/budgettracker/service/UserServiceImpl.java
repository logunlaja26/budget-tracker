package com.lyomann.budgettracker.service;

import com.lyomann.budgettracker.document.BudgetAllowance;
import com.lyomann.budgettracker.document.User;
import com.lyomann.budgettracker.dto.BudgetAllowanceDto;
import com.lyomann.budgettracker.dto.UserDto;
import com.lyomann.budgettracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;


@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final ModelMapper modelMapper;

    @Override
    public void createUser(UserDto userDto) {
        User user = userRepository.createUser(convertToUser(userDto));
        log.info("New user has registered - {} with budget allowances {}", user.getUsername(), user.getBudgetAllowances());
    }

    @Override
    public UserDto getUser(String username) {
        User user = userRepository.getUser(username);
        return convertToUserDto(user);
    }

    @Override
    public void updateBudgetAllowance(String username, String category, BudgetAllowanceDto updatedBudgetAllowanceDto) {
        userRepository.updateBudgetAllowance(username, category, convertToBudgetAllowance(updatedBudgetAllowanceDto));
    }

    @Override
    public void addBudgetAllowance(String username, BudgetAllowanceDto budgetAllowanceDto) {
        userRepository.addBudgetAllowance(username, convertToBudgetAllowance(budgetAllowanceDto));

    }

    private UserDto convertToUserDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    private User convertToUser(UserDto userDto) {
        return modelMapper.map(userDto, User.class);
    }

    private BudgetAllowance convertToBudgetAllowance(BudgetAllowanceDto budgetAllowanceDto) {
        return modelMapper.map(budgetAllowanceDto, BudgetAllowance.class);
    }
}
