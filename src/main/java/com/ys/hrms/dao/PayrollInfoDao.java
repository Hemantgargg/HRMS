package com.ys.hrms.dao;

import com.ys.hrms.entity.PayrollInfo;
import com.ys.hrms.repository.PayrollInfoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class PayrollInfoDao {

    private final PayrollInfoRepository payrollInfoRepository;

    @Autowired
    public PayrollInfoDao(PayrollInfoRepository payrollInfoRepository) {
        this.payrollInfoRepository = payrollInfoRepository;
    }

    // Save a single PayrollInfo
    public PayrollInfo savePayrollInfo(PayrollInfo payrollInfo) {
        return payrollInfoRepository.save(payrollInfo);
    }

    // Save multiple PayrollInfo records
    public List<PayrollInfo> savePayrollInfos(List<PayrollInfo> payrollInfos) {
        return payrollInfoRepository.saveAll(payrollInfos);
    }

    // Find by ID
    public Optional<PayrollInfo> getPayrollInfoById(int id) {
        return payrollInfoRepository.findById(id);
    }

    // Get all PayrollInfo records
    public List<PayrollInfo> getAllPayrollInfos() {
        return payrollInfoRepository.findAll();
    }

    // Update PayrollInfo
    public PayrollInfo updatePayrollInfo(int id, PayrollInfo updatedPayrollInfo) {
        Optional<PayrollInfo> existingPayrollInfoOpt = payrollInfoRepository.findById(id);
        if (existingPayrollInfoOpt.isPresent()) {
            PayrollInfo existingPayrollInfo = existingPayrollInfoOpt.get();
            existingPayrollInfo.setBankAccountDetails(updatedPayrollInfo.getBankAccountDetails());
            existingPayrollInfo.setPayrollCycle(updatedPayrollInfo.getPayrollCycle());
            existingPayrollInfo.setSalaryStructure(updatedPayrollInfo.getSalaryStructure());
            existingPayrollInfo.setTaxDeductions(updatedPayrollInfo.getTaxDeductions());
            return payrollInfoRepository.save(existingPayrollInfo);
        }
        return null; // or throw an exception
    }

    // Delete PayrollInfo by ID
    public void deletePayrollInfo(int id) {
        payrollInfoRepository.deleteById(id);
    }

    // Check existence of PayrollInfo
    public boolean payrollInfoExists(int id) {
        return payrollInfoRepository.existsById(id);
    }
}
