package com.example.demo;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
public class SecurityConfig{

   @Bean
   public UserDetailsService userDetailsService() {
      UserDetails userDetails = User.withDefaultPasswordEncoder().username("user").password("user").roles("USER").build();
      return new InMemoryUserDetailsManager(userDetails);
   }
}