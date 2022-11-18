package com.maingocdieu.shopping.service.impl;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.maingocdieu.shopping.model.Role;
import com.maingocdieu.shopping.model.RoleName;
import com.maingocdieu.shopping.repository.IRoleRepository;
import com.maingocdieu.shopping.service.iRoleService;

@Service
public class RoleServiceImpl implements iRoleService{

  @Autowired
  IRoleRepository roleRepository;
  @Override
  public Optional<Role> findByName(RoleName name) {
    return roleRepository.findByName(name);
  }

}
