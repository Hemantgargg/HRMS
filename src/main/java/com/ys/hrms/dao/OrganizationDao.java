package com.ys.hrms.dao;

import com.ys.hrms.entity.Organization;
import com.ys.hrms.repository.OrganizationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class OrganizationDao {

    private final OrganizationRepository organizationRepository;

    @Autowired
    public OrganizationDao(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    // Save a single organization
    public Organization saveOrganization(Organization organization) {
        return organizationRepository.save(organization);
    }

    // Save multiple organizations
    public List<Organization> saveOrganizations(List<Organization> organizations) {
        return organizationRepository.saveAll(organizations);
    }
    
    // Find by registration number
    public Organization findByRegistrationNumber(String registrationNumber) {
        return organizationRepository.findByRegistrationNumber(registrationNumber);
    }

    // Find by tax identification number
    public Organization findByTaxIdentificationNumber(String taxIdentificationNumber) {
        return organizationRepository.findByTaxIdentificationNumber(taxIdentificationNumber);
    }

    // Find by website URL
    public List<Organization> findByWebsiteUrl(String websiteUrl) {
        return organizationRepository.findByWebsiteUrl(websiteUrl);
    }


    // Update an organization
    public Organization updateOrganization(int id, Organization updatedOrganization) {
        Optional<Organization> existingOrganizationOpt = organizationRepository.findById(id);
        if (existingOrganizationOpt.isPresent()) {
            Organization existingOrganization = existingOrganizationOpt.get();
            // Update fields as needed
            existingOrganization.setName(updatedOrganization.getName());
            existingOrganization.setRegistrationNumber(updatedOrganization.getRegistrationNumber());
            existingOrganization.setTaxIdentificationNumber(updatedOrganization.getTaxIdentificationNumber());
            existingOrganization.setIndustryType(updatedOrganization.getIndustryType());
            existingOrganization.setOrganizationType(updatedOrganization.getOrganizationType());
            existingOrganization.setIncorporationDate(updatedOrganization.getIncorporationDate());
            existingOrganization.setWebsiteUrl(updatedOrganization.getWebsiteUrl());
            existingOrganization.setHeadOfficeAddress(updatedOrganization.getHeadOfficeAddress());
            return organizationRepository.save(existingOrganization);
        }
        return null; // or throw an exception
    }

    // Get all organizations
    public List<Organization> getAllOrganizations() {
        return organizationRepository.findAll();
    }

    // Get organizations by specific criteria
    public List<Organization> getOrganizationsByName(String name) {
        return organizationRepository.findByName(name);
    }
    
   
    // Sort organizations dynamically
    public List<Organization> sortOrganizations(Sort sort) {
        return organizationRepository.findAll(sort);
    }
}
