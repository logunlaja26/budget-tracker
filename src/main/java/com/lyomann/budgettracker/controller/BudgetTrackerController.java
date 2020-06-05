package com.lyomann.budgettracker.controller;

import com.google.cloud.firestore.v1.FirestoreAdminClient;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.UserRecord;
import com.google.firebase.auth.UserRecord.CreateRequest;
import com.google.firebase.auth.internal.FirebaseCustomAuthToken;
import com.google.firebase.auth.internal.FirebaseTokenFactory;
import com.lyomann.budgettracker.dto.TokenDto;
import com.lyomann.budgettracker.dto.UserCreationDto;
import com.lyomann.budgettracker.dto.UserDto;
import com.lyomann.budgettracker.dto.UserLoginDto;
import com.lyomann.budgettracker.exception.UserRegistrationException;
import com.lyomann.budgettracker.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Slf4j
public class BudgetTrackerController {

    private final UserService userService;

    private final FirebaseAuth firebaseAuth;

    @PostMapping("/users")
    @ResponseStatus(code = HttpStatus.CREATED)
    public void registerUser(@RequestBody UserCreationDto userCreationDto) {
        userService.createUser(userCreationDto);

        CreateRequest createRequest = new CreateRequest()
                .setUid(userCreationDto.getUsername())
                .setEmail(userCreationDto.getEmail())
                .setEmailVerified(false)
                .setPassword(userCreationDto.getPassword())
                .setDisplayName(userCreationDto.getUsername())
                .setDisabled(false);

        try {
            UserRecord user = firebaseAuth.createUser(createRequest);
            firebaseAuth.generateEmailVerificationLink(userCreationDto.getEmail());
            log.info("User created in firebase: {} ", user.getUid());
        } catch (FirebaseAuthException ex) {
            log.error("User was not created in firebase", ex);
            throw new UserRegistrationException("User registration failed.");
        }
    }

    @PostMapping("/authenticate")
    public TokenDto userLogin(@RequestBody UserLoginDto userLoginDto) {
        try {
            String token = firebaseAuth.createCustomToken(userLoginDto.getUsername());
            return TokenDto.builder()
                    .username(userLoginDto.getUsername())
                    .jwtToken(token)
                    .build();
        } catch (FirebaseAuthException ex) {
            log.error("User was not able to be logged in", ex);
            throw new UserRegistrationException("User login failed.");
        }
    }
}
