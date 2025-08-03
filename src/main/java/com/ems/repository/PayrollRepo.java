package com.ems.repository;

import com.ems.entity.Payroll;
import com.ems.entity.UserPayrolls;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface PayrollRepo extends JpaRepository<Payroll, String> {

    Payroll findByUsername(String username);

}
