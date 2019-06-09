package com.cointest.util;

import org.springframework.stereotype.Service;

/**
 * Created by edward on 7/15/17.
 */
@Service
public class CustomMessageType {
    private String successMessage;

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }
}
