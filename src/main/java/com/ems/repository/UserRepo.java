package com.ems.repository;

import com.ems.entity.User;
import com.ems.enums.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface UserRepo extends JpaRepository<User, String> {

    boolean existsByEmail(String email);

    User findByUsername(String username);
    
    long countUserByRole(Role role);

}
