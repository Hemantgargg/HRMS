package com.ys.hrms.entity;

import lombok.Data;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "payroll_info")
public class PayrollInfo {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    private String bankAccountDetails;
    private String payrollCycle;

    @Column(columnDefinition = "TEXT")
    private String salaryStructure;

    @Column(columnDefinition = "TEXT")
    private String taxDeductions;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
