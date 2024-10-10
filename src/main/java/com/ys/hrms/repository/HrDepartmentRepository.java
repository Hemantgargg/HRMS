package com.ys.hrms.repository;

import com.ys.hrms.entity.HrDepartment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface HrDepartmentRepository extends JpaRepository<HrDepartment, Integer> {

    // Get all HR departments for a specific organization
    @Query("SELECT h FROM HrDepartment h WHERE h.organization.id = ?1")
    List<HrDepartment> findByOrganizationId(int organizationId);

    // Find HR departments by HR head name
    List<HrDepartment> findByHrHeadName(String hrHeadName);

    // Find HR departments by HR head contact
    List<HrDepartment> findByHrHeadContact(String hrHeadContact);
    

    
}
