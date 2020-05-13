package com.decagon.stock.dto.response;

/**
 * @author Victor.Komolafe
 */
public interface ResponseData {
    // codes
    int SUCCESS = 0;
    int FAILURE = 1;
    int RETRY = 2;
    int DATA_NOT_FOUND = 3;

    // descriptions
    String SUCCESS_MESSAGE = "Success";
    String USER_VALIDATION_ERROR_MESSAGE = "User validation error";
    String USER_EXISTS_MESSAGE = "User exists";
    String USER_NOT_EXITS_MESSAGE = "User does not exists";
    String INVALID_DATA_MESSAGE = "Invalid request details";
    String STOCK_OVERBUY_MESSAGE = "Stock already over bought";
}
