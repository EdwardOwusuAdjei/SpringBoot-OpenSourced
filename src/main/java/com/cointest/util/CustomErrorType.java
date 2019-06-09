package com.cointest.util;

import org.springframework.stereotype.Service;

@Service
public class CustomErrorType {

    private String errorMessage;

    public CustomErrorType(){};
    public CustomErrorType(String errorMessage){
        this.errorMessage = errorMessage;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }
}
