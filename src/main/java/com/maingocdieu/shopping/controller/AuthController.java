package com.maingocdieu.shopping.controller;

import java.util.HashSet;
import java.util.Set;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maingocdieu.shopping.dto.request.SignInForm;
import com.maingocdieu.shopping.dto.request.SignUpForm;
import com.maingocdieu.shopping.dto.response.JwtResponse;
import com.maingocdieu.shopping.dto.response.ResponseMessage;
import com.maingocdieu.shopping.model.Role;
import com.maingocdieu.shopping.model.RoleName;
import com.maingocdieu.shopping.model.User;
import com.maingocdieu.shopping.security.jwt.JwtProvider;
import com.maingocdieu.shopping.security.userprincal.UserPrinciple;
import com.maingocdieu.shopping.service.impl.RoleServiceImpl;
import com.maingocdieu.shopping.service.impl.UserServiceImpl;

@RequestMapping("/api/auth")
@RestController
public class AuthController {

  @Autowired
  UserServiceImpl userServiceImpl;
  @Autowired
  RoleServiceImpl roleServiceImpl;

  @Autowired
  PasswordEncoder passwordEncoder;

  @Autowired
  AuthenticationManager authenticationManager;
  
  @Autowired
  JwtProvider jwtProvider;


  @PostMapping("/signup")
  public ResponseEntity<?> register(@Valid @RequestBody SignUpForm signUpForm) {
    if (userServiceImpl.existsByUsername(signUpForm.getUsername())) {
      return new ResponseEntity<ResponseMessage>(new ResponseMessage("The username exist"),
          HttpStatus.OK);
    }
    if (userServiceImpl.existsByEmail(signUpForm.getEmail())) {
      return new ResponseEntity<ResponseMessage>(new ResponseMessage("The email exist"),
          HttpStatus.OK);
    }

    User user = new User(signUpForm.getName(), signUpForm.getUsername(), signUpForm.getEmail(),
        passwordEncoder.encode(signUpForm.getPassword()));

    Set<String> strRoles = signUpForm.getRoles();

    Set<Role> roles = new HashSet<>();
    strRoles.forEach(role -> {
      switch (role) {
        case "admin":
          Role adminRole = roleServiceImpl.findByName(RoleName.ADMIN)
              .orElseThrow(() -> new RuntimeException("Role not found"));
          roles.add(adminRole);
          break;

        case "pm":
          Role pmRole = roleServiceImpl.findByName(RoleName.PM)
              .orElseThrow(() -> new RuntimeException("Role not found"));
          roles.add(pmRole);
          break;

        default: {
          Role userRole = roleServiceImpl.findByName(RoleName.USER)
              .orElseThrow(() -> new RuntimeException("Role not found"));
          roles.add(userRole);
        }
      }
    });
    user.setRoles(roles);
    userServiceImpl.save(user);
    return new ResponseEntity<>(new ResponseMessage("Create user success"), HttpStatus.OK);
  }

  @PostMapping("/signin")
  public ResponseEntity<?> login(@RequestBody SignInForm signInForm) {
    Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(signInForm.getUsername(), signInForm.getPassword()));
    SecurityContextHolder.getContext().setAuthentication(authentication);
    String token = jwtProvider.createToken(authentication);
    UserPrinciple userPrinciple =(UserPrinciple) authentication.getPrincipal();
    return new ResponseEntity<>(new JwtResponse(token, userPrinciple.getName(), userPrinciple.getAuthorities()), HttpStatus.OK);
  }
}
