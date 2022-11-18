package com.maingocdieu.shopping.service;

import java.util.Optional;
import com.maingocdieu.shopping.model.Role;
import com.maingocdieu.shopping.model.RoleName;

public interface iRoleService {
  Optional<Role> findByName(RoleName name);
}
