package com.ys.hrms.repository;

import com.ys.hrms.entity.Contact;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<Contact, Integer> {
    // Find contacts by organization ID
	
    List<Contact> findByOrganizationId(int organizationId);
}
