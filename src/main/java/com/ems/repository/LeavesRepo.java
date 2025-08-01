package com.ems.repository;

import com.ems.entity.Leaves;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LeavesRepo extends JpaRepository<Leaves, String> {

    // Method to find leaves by username
    List<Leaves> findByUsername(String username);

    Optional<Leaves> findById(String id);
}
