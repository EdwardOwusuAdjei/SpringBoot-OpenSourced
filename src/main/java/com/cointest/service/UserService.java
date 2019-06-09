package com.cointest.service;

import java.util.List;

import com.cointest.model.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    void save(User user);
    User findByUsername(String username);
    boolean isUserExist(User user);
    List<User> findAllUsers();
    User findById(Long id);
    User updateUser(User user);

}
