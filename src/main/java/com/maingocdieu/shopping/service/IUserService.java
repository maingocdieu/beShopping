package com.maingocdieu.shopping.service;

import java.util.Optional;
import com.maingocdieu.shopping.model.User;

public interface IUserService {
  Optional<User> findByUsername(String name); // Tim kiem user co ton tai trong db khong

  Boolean existsByUsername(String username); // user name da co trong db hay chua

  Boolean existsByEmail(String email); // kiem tra email da ton tai trong db hay chua
  
  User save(User user);
}
