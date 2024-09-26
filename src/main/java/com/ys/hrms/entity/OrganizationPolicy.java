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
@Table(name = "organization_policies")
public class OrganizationPolicy {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    private String workingHours;

    @Column(columnDefinition = "TEXT")
    private String shiftPatterns;

    @Column(columnDefinition = "TEXT")
    private String leavePolicies;

    @Column(columnDefinition = "TEXT")
    private String codeOfConduct;

    private String documentUpload; // URL or File link

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
