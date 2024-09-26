package com.ys.hrms.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;

import com.ys.hrms.entity.Organization;

public interface OrganizationRepository extends JpaRepository<Organization, Integer> {

    List<Organization> findByName(String name);

    @Query("SELECT o FROM Organization o WHERE o.registrationNumber = ?1")
    Organization findByRegistrationNumber(String registrationNumber);

    @Query("SELECT o FROM Organization o WHERE o.taxIdentificationNumber = ?1")
    Organization findByTaxIdentificationNumber(String taxIdentificationNumber);
    
    @Query("SELECT o FROM Organization o WHERE o.websiteUrl = ?1")
    List<Organization> findByWebsiteUrl(String websiteUrl);



    
}
