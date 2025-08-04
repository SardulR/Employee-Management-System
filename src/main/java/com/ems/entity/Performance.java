package com.ems.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "performance")
@Data
public class Performance {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "performance_score", nullable = false)
    private float performanceScore;
}
