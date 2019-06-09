package com.cointest.json;

/**
 * Created by edward on 7/17/17.
 */
public class Token {
    private String token;
    private String newPassword;
    private String confirmNewPassword;

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getToken() {
        return token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getConfirmNewPassword() {
        return confirmNewPassword;
    }

    public void setConfirmNewPassword(String confirmNewPassword) {
        this.confirmNewPassword = confirmNewPassword;
    }
}
