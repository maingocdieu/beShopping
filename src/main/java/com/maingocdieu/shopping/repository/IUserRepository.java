package com.maingocdieu.shopping.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maingocdieu.shopping.model.User;

@Repository
public interface IUserRepository extends JpaRepository<User, Long>{
  
  Optional<User> findByUsername(String name); //Tim kiem user co ton tai trong db khong
  
  Boolean existsByUsername(String username); // user name da co trong db hay chua
  
  Boolean existsByEmail(String email); // kiem tra email da ton tai trong db hay chua
}
