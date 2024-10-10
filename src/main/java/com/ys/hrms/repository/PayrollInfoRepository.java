package com.ys.hrms.repository;

import com.ys.hrms.entity.PayrollInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PayrollInfoRepository extends JpaRepository<PayrollInfo, Integer> {

    // Find PayrollInfo by organization ID
    List<PayrollInfo> findByOrganizationId(int organizationId);

    // Additional custom query methods can be added here
    // For example, finding payroll info by specific attributes if needed
}
