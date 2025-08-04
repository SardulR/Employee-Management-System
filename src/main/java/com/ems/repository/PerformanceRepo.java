package com.ems.repository;

import com.ems.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepo extends JpaRepository<Performance, Long> {

    Performance findByUsername(String username);
}
