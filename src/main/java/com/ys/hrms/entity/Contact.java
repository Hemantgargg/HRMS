package com.ys.hrms.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Data 
@Entity 
@Table(name = "contacts")
public class Contact {
    
    @Id 
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "organization_id", nullable = false)
    private Organization organization;

    private String contactPersonName;
    private String contactPersonDesignation;
    private String contactEmail;
    private String contactPhone;
    private String contactAddress;
    private long contactAadharNum;
    private String contactPanNum;

    @Column(nullable = false)
    private LocalDateTime createdAt = LocalDateTime.now();
}
