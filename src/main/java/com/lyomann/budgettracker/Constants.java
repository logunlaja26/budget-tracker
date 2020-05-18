package com.lyomann.budgettracker;

import org.springframework.data.mongodb.core.query.Query;

import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

public class Constants {
    public static final String USERS_COLLECTION = "users";
    public static final String NEW_USER_EXPENSES_ERROR_MESSAGE = "New users cannot have more than one expense added";
    public static final String UPDATE_EXPENSES_ERROR_MESSAGE = "User must exist to update expenses";
    public static final String USER_DOES_NOT_EXIST_ERROR_MESSAGE = "This user does not exist in the database";

    public static Query findByUsernameQuery(String username) { return query(where("username").is(username)); }
}
