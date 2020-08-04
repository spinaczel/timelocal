package com.timeactuall.demo.security.service;

import com.timeactuall.demo.security.model.User;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface UserService  {
    User findByUsername(String email);
    User save(User user);
}
