package com.maingocdieu.shopping.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.maingocdieu.shopping.security.jwt.JwtTokenFilter;
import com.maingocdieu.shopping.security.userprincal.UserPrinciple;

@RestController
public class ProductCotroller {
  
  @Autowired
  JwtTokenFilter jwtTokenFilter;
  
  @GetMapping(value ="/product", produces = "application/json")
  public String getBook() {
    
    System.out.println(SecurityContextHolder.getContext().getAuthentication().getPrincipal());
    UserPrinciple a =(UserPrinciple) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    System.out.println(a.getUsername());
      return "Hello";
  }
  
}                                         
