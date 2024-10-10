package com.ys.hrms.controller;

import com.ys.hrms.dao.HrDepartmentDao;
import com.ys.hrms.entity.HrDepartment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping(value = "/hrdepartment/api")
public class HrDepartmentController {

    @Autowired
    private HrDepartmentDao hrDepartmentDao;

    // Get current date
    @GetMapping(value = "/getTodayDate")
    public String getTodayDate() {
        return "Today date is " + java.time.LocalDate.now();
    }

    // Save a single HR department
    @PostMapping(value = "/saveHrDepartment")
    public ResponseEntity<Object> saveHrDepartment(@RequestBody HrDepartment hrDepartment) {
        try {
            // Ensure the organization is set
            if (hrDepartment.getOrganization() == null) {
                return new ResponseEntity<>("Organization must not be null.", HttpStatus.BAD_REQUEST);
            }
            
            // Ensure the organization ID is set
            if (hrDepartment.getOrganization().getId()<= 0) {
                return new ResponseEntity<>("Organization ID must not be null.", HttpStatus.BAD_REQUEST);
            }

            // Save the HR department
            HrDepartment savedHrDepartment = hrDepartmentDao.saveHrDepartment(hrDepartment);
            System.out.println(hrDepartment);
            return new ResponseEntity<>(savedHrDepartment, HttpStatus.CREATED);
        } catch (DataIntegrityViolationException ex) {
            return new ResponseEntity<>("Database constraint violation: " + ex.getMessage(), HttpStatus.CONFLICT);
        } catch (Exception ex) {
            return new ResponseEntity<>("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    // Save multiple HR departments
    @PostMapping(value = "/saveMultipleHrDepartments")
    public ResponseEntity<Object> saveMultipleHrDepartments(@RequestBody List<HrDepartment> hrDepartments) {
        List<HrDepartment> savedHrDepartments = new ArrayList<>();
        List<Map<String, String>> failedDepartments = new ArrayList<>();

        for (HrDepartment dept : hrDepartments) {
            try {
                // Save each HR department
                HrDepartment savedDept = hrDepartmentDao.saveHrDepartment(dept);
                savedHrDepartments.add(savedDept);
            } catch (DataIntegrityViolationException ex) {
                Map<String, String> errorInfo = new HashMap<>();
                errorInfo.put("hrDepartment", dept.getHrHeadName());
                errorInfo.put("error", "Database error: " + ex.getMessage());
                failedDepartments.add(errorInfo);
            } catch (Exception ex) {
                Map<String, String> errorInfo = new HashMap<>();
                errorInfo.put("hrDepartment", dept.getHrHeadName());
                errorInfo.put("error", "An unexpected error occurred: " + ex.getMessage());
                failedDepartments.add(errorInfo);
            }
        }

        Map<String, Object> response = new HashMap<>();
        response.put("savedHrDepartments", savedHrDepartments);
        response.put("failedDepartments", failedDepartments);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    // Get HR department by ID
    @GetMapping(value = "/getHrDepartmentById/{id}")
    public ResponseEntity<HrDepartment> getHrDepartmentById(@PathVariable("id") int id) {
        Optional<HrDepartment> hrDepartment = hrDepartmentDao.getHrDepartmentById(id);
        if (hrDepartment.isPresent()) {
            HrDepartment dept = hrDepartment.get();
            // Optionally log or manipulate data if needed
            return new ResponseEntity<>(dept, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }


    // Get all HR departments
    @GetMapping(value = "/displayAllHrDepartments")
    public List<HrDepartment> displayAllHrDepartments() {
        return hrDepartmentDao.getAllHrDepartments();
    }

    // Update HR department by ID
    @PutMapping(value = "/updateHrDepartment/{id}")
    public ResponseEntity<HrDepartment> updateHrDepartment(@PathVariable("id") int id,
            @RequestBody HrDepartment hrDepartment) {
        HrDepartment updatedHrDepartment = hrDepartmentDao.updateHrDepartment(id, hrDepartment);
        if (updatedHrDepartment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(updatedHrDepartment, HttpStatus.OK);
    }


    // Find HR departments by organization ID
    @GetMapping(value = "/getHrDepartmentsByOrganizationId/{organizationId}")
    public List<HrDepartment> getHrDepartmentsByOrganizationId(@PathVariable int organizationId) {
        return hrDepartmentDao.getHrDepartmentsByOrganizationId(organizationId);
    }

    // Sort HR departments dynamically
    @GetMapping(value = "/sortHrDepartments")
    public List<HrDepartment> sortHrDepartments(@RequestParam String sortBy,
            @RequestParam(defaultValue = "ASC") String direction) {
        Sort sort = Sort.by(Sort.Direction.fromString(direction), sortBy);
        return hrDepartmentDao.sortHrDepartments(sort);
    }
}
