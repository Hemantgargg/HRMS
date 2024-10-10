package com.ys.hrms.controller;

import com.ys.hrms.dao.OrganizationDao;
import com.ys.hrms.entity.Organization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/organization/api")
public class OrganizationController {

	@Autowired
	private OrganizationDao organizationDao;

	// Endpoint to get the current date
	@GetMapping(value = "/getTodayDate")
	public String getTodayDate() {
		return "Today date is " + java.time.LocalDate.now();
	}

	// Save a single organization
	@PostMapping(value = "/saveOrganization")
	public ResponseEntity<Object> saveOrganization(@RequestBody Organization organization) {
		System.out.println(organization); 
		try {
			// Check for duplicate records based on registration number, tax ID, or website
			// URL
			if (organizationDao.findByRegistrationNumber(organization.getRegistrationNumber()) != null ) {
				return new ResponseEntity<>(
						"Duplicate Registration Number found: " + organization.getRegistrationNumber(),
						HttpStatus.CONFLICT);
			}
			if (organizationDao.findByTaxIdentificationNumber(organization.getTaxIdentificationNumber()) != null) {
				return new ResponseEntity<>(
						"Duplicate Tax Identification Number found: " + organization.getTaxIdentificationNumber(),
						HttpStatus.CONFLICT);
			}

			// Save the organization if no duplicates are found
			Organization savedOrganization = organizationDao.saveOrganization(organization);
			return new ResponseEntity<>(savedOrganization, HttpStatus.CREATED);
		} catch (DataIntegrityViolationException ex) {
			// Handle database constraint violations
			return new ResponseEntity<>("Duplicate data error: " + ex.getMessage(), HttpStatus.CONFLICT);
		} catch (Exception ex) {
			// Handle any other exceptions
			return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
	}

	// Save multiple organizations
	@PostMapping(value = "/saveMultipleOrganizations")
	public ResponseEntity<Object> saveMultipleOrganizations(@RequestBody List<Organization> organizations) {
	    List<Organization> savedOrganizations = new ArrayList<>();
	    List<Map<String, String>> failedOrganizations = new ArrayList<>();

	    for (Organization org : organizations) {
	        try {
	            boolean nameExists = !organizationDao.getOrganizationsByName(org.getName()).isEmpty();
	            boolean registrationNumberExists = organizationDao.findByRegistrationNumber(org.getRegistrationNumber()) != null;
	            boolean taxIdExists = organizationDao.findByTaxIdentificationNumber(org.getTaxIdentificationNumber()) != null;
	            boolean websiteExists = !organizationDao.findByWebsiteUrl(org.getWebsiteUrl()).isEmpty();

	            if (nameExists || registrationNumberExists || taxIdExists || websiteExists) {
	                String duplicateField = nameExists ? "name" :
	                        registrationNumberExists ? "registration number" :
	                        taxIdExists ? "tax ID" : "website URL";

	                Map<String, String> errorInfo = new HashMap<>();
	                errorInfo.put("organization", org.getName());
	                errorInfo.put("error", "Duplicate data found for " + duplicateField);

	                failedOrganizations.add(errorInfo);
	            } else {
	                // If no duplicates, save the organization
	                Organization savedOrg = organizationDao.saveOrganization(org); // Adjust save method as needed
	                savedOrganizations.add(savedOrg);
	            }
	        } catch (DataIntegrityViolationException ex) {
	            // Handle database-level errors
	            Map<String, String> errorInfo = new HashMap<>();
	            errorInfo.put("organization", org.getName());
	            errorInfo.put("error", "Database error: " + ex.getMessage());

	            failedOrganizations.add(errorInfo);
	        } catch (Exception ex) {
	            // Handle any other exceptions
	            Map<String, String> errorInfo = new HashMap<>();
	            errorInfo.put("organization", org.getName());
	            errorInfo.put("error", "An unexpected error occurred: " + ex.getMessage());

	            failedOrganizations.add(errorInfo);
	        }
	    }

	    // Prepare the final response containing both successes and failures
	    Map<String, Object> response = new HashMap<>();
	    response.put("savedOrganizations", savedOrganizations);
	    response.put("failedOrganizations", failedOrganizations);

	    // Return status OK but include details of failures in the response body
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	// Get organization by ID
	@GetMapping(value = "/getOrganizationById/{id}")
	public ResponseEntity<Organization> getOrganizationById(@PathVariable("id") int id) {
		Organization organization = organizationDao.getAllOrganizations().stream().filter(org -> org.getId() == id)
				.findFirst().orElse(null);

		if (organization == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(organization, HttpStatus.OK);
	}

	

	// Get all organizations
	@GetMapping(value = "/displayAllOrganizations")
	public List<Organization> displayAllOrganizations() {
		System.out.println(organizationDao.getAllOrganizations());
		return organizationDao.getAllOrganizations();
	}

	// Update organization by ID
	@PutMapping(value = "/updateOrganization/{id}")
	public ResponseEntity<Organization> updateOrganization(@PathVariable("id") int id,
			@RequestBody Organization organization) {
		Organization updatedOrganization = organizationDao.updateOrganization(id, organization);

		if (updatedOrganization == null) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(updatedOrganization, HttpStatus.OK);
	}

	// Find organizations by name
	@GetMapping(value = "/getOrganizationsByName/{name}")
	public List<Organization> getOrganizationsByName(@PathVariable String name) {
		return organizationDao.getOrganizationsByName(name);
	}

	// Sort organizations dynamically
	@GetMapping(value = "/sortOrganizations")
	public List<Organization> sortOrganizations(@RequestParam String sortBy,
			@RequestParam(defaultValue = "ASC") String direction) {
		Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
		return organizationDao.sortOrganizations(sort);
	}

	// Pagination example (not fully implemented, adjust according to your
	// requirements)
	@GetMapping(value = "/pagingOrganizations")
	public Page<Organization> pagingOrganizations(@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "20") int size) {
		// Implement pagination logic here
		return null; // Placeholder
	}
}
