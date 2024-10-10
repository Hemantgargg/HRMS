package com.ys.hrms.dao;

import com.ys.hrms.entity.HrDepartment;
import com.ys.hrms.repository.HrDepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class HrDepartmentDao {

    private final HrDepartmentRepository hrDepartmentRepository;

    @Autowired
    public HrDepartmentDao(HrDepartmentRepository hrDepartmentRepository) {
        this.hrDepartmentRepository = hrDepartmentRepository;
    }

    // Save a single HR department
    public HrDepartment saveHrDepartment(HrDepartment hrDepartment) {
        return hrDepartmentRepository.save(hrDepartment);
    }

    // Save multiple HR departments
    public List<HrDepartment> saveHrDepartments(List<HrDepartment> hrDepartments) {
        return hrDepartmentRepository.saveAll(hrDepartments);
    }

    // Find by ID
    public Optional<HrDepartment> getHrDepartmentById(int id) {
        return hrDepartmentRepository.findById(id);
    }

    // Get all HR departments
    public List<HrDepartment> getAllHrDepartments() {
        return hrDepartmentRepository.findAll();
    }

    // Update HR department
    public HrDepartment updateHrDepartment(int id, HrDepartment updatedHrDepartment) {
        Optional<HrDepartment> existingHrDepartmentOpt = hrDepartmentRepository.findById(id);
        if (existingHrDepartmentOpt.isPresent()) {
            HrDepartment existingHrDepartment = existingHrDepartmentOpt.get();
            existingHrDepartment.setHrHeadName(updatedHrDepartment.getHrHeadName());
            existingHrDepartment.setHrHeadContact(updatedHrDepartment.getHrHeadContact());
            existingHrDepartment.setHrPolicies(updatedHrDepartment.getHrPolicies());
            existingHrDepartment.setOrganization(updatedHrDepartment.getOrganization());
            return hrDepartmentRepository.save(existingHrDepartment);
        }
        return null;
    }

    // Delete HR department by ID
    public void deleteHrDepartment(int id) {
        hrDepartmentRepository.deleteById(id);
    }

    // Check existence of HR department
    public boolean hrDepartmentExists(int id) {
        return hrDepartmentRepository.existsById(id);
    }

    // Get HR departments by Organization ID
    public List<HrDepartment> getHrDepartmentsByOrganizationId(int organizationId) {
        return hrDepartmentRepository.findByOrganizationId(organizationId);
    }

    // Sort HR departments dynamically
    public List<HrDepartment> sortHrDepartments(Sort sort) {
        return hrDepartmentRepository.findAll(sort);
    }

    // Get HR departments with pagination
    public Page<HrDepartment> getHrDepartmentsPaginated(Pageable pageable) {
        return hrDepartmentRepository.findAll(pageable);
    }

    // Find HR departments by name
    public List<HrDepartment> getHrDepartmentsByName(String name) {
        return hrDepartmentRepository.findByHrHeadName(name);
    }
}
