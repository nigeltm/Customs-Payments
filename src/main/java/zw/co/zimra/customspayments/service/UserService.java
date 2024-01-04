package zw.co.zimra.customspayments.service;


import zw.co.zimra.customspayments.dto.LoginResponse;
import zw.co.zimra.customspayments.dto.Transaction;
import zw.co.zimra.customspayments.model.User;

import java.util.List;

public interface UserService {
    public User saveUser(User user);
    public User getUser(String username);

    LoginResponse login(String username, String password);
}
