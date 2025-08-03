package com.ems.repository;

import com.ems.entity.UserPayrolls;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserPayrollsRepo extends JpaRepository<UserPayrolls, Long> {

    UserPayrolls findById(long id);

    UserPayrolls findByMonth(String month);
}
