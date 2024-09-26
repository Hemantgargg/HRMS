package com.ys.hrms.entity;

import lombok.Data;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;
import java.util.Date;

@Data
@Entity
@Table(name = "organizations")
public class Organization {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, unique = true)
    private String registrationNumber;
    @Column(nullable = false, unique = true)
    private String taxIdentificationNumber;
    private String industryType;
    private String organizationType;
    private Date incorporationDate;
    @Column(nullable = false, unique = true)
    private String websiteUrl;

    @Column(columnDefinition = "TEXT")
    private String headOfficeAddress;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
