package zw.co.zimra.customspayments.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import zw.co.zimra.customspayments.dto.Transaction;
import zw.co.zimra.customspayments.exception.ResourceNotFoundException;
import zw.co.zimra.customspayments.model.User;
import zw.co.zimra.customspayments.repository.UserRepository;
import zw.co.zimra.customspayments.service.UserService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    public User saveUser(User user){
        return userRepository.save(user);
    }

    public User getUser(String username) {
        return userRepository.findByUsername(username).orElseThrow(()-> new ResourceNotFoundException(String.format("User with username: %s not found.",username)));
    }


}
