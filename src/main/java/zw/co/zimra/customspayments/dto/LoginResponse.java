package zw.co.zimra.customspayments.dto;

import zw.co.zimra.customspayments.model.User;

public class LoginResponse {
    private boolean isAuthenticated;
    private User user;

    public LoginResponse() {
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public void setAuthenticated(boolean authenticated) {
        isAuthenticated = authenticated;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
