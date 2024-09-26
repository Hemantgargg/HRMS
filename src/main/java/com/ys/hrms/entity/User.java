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
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Data
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    @Column(nullable = false, unique = true)
    private String employeeCode;

    @OneToOne
    @JoinColumn(name = "phone_number", referencedColumnName = "contactPhone")
    private Contact contact;

    private String department;
    private String designation;
    private String branch;
    private String officialEmail;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String password; // Hashed password

    private String role; // Administrator, HR Manager, etc.
    private int noticePeriod;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
