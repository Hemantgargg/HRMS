package com.ys.hrms.repository;

import com.ys.hrms.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UserRepository extends JpaRepository<User, Integer> {
    // Find users by organization ID
    List<User> findByOrganizationId(int organizationId);

    // Find users by username
    User findByUsername(String username);
    
    // Find users by employee code
    User findByEmployeeCode(String employeeCode);
}
