package com.ems.entity;

import com.ems.enums.PayRollStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "user_payrolls")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserPayrolls {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(name = "basic_salary", nullable = false)
    private double basicSalary;

    @Column(nullable = false)
    private double bonus;

    @Column(nullable = false)
    private double deductions;

    @Column(nullable = false, name = "net_salary")
    private double netSalary;

    @Column(nullable = false)
    private String month;

    private PayRollStatus status;

    private LocalDate generatedAt;
}
