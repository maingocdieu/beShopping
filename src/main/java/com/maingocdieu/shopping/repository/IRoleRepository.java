package com.maingocdieu.shopping.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.maingocdieu.shopping.model.Role;
import com.maingocdieu.shopping.model.RoleName;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {
  Optional<Role> findByName(RoleName name);
}
