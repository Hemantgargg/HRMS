package com.ys.hrms.entity;

import lombok.Data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Data
@Entity 
@Table(name = "hr_departments")
public class HrDepartment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    private String hrHeadName;
    private String hrHeadContact;
    private String hrPolicies; // URL or File Link to HR Policies

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
