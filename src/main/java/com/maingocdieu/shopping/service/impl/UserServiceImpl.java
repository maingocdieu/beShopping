package com.maingocdieu.shopping.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maingocdieu.shopping.model.User;
import com.maingocdieu.shopping.repository.IUserRepository;
import com.maingocdieu.shopping.service.IUserService;

@Service
public class UserServiceImpl implements IUserService {
  
  @Autowired
  IUserRepository userRepository;

  @Override
  public Optional<User> findByUsername(String name) {
    return userRepository.findByUsername(name);
  }

  @Override
  public Boolean existsByUsername(String username) {
    return userRepository.existsByUsername(username);
  }

  @Override
  public Boolean existsByEmail(String email) {
    return userRepository.existsByEmail(email);
  }

  @Override
  public User save(User user) {
    return userRepository.save(user);
  }

}
