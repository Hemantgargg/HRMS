package com.ys.hrms.controller;

import com.ys.hrms.dao.PayrollInfoDao;
import com.ys.hrms.entity.PayrollInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/payroll-info")
public class PayrollInfoController {

    private final PayrollInfoDao payrollInfoDao;

    @Autowired
    public PayrollInfoController(PayrollInfoDao payrollInfoDao) {
        this.payrollInfoDao = payrollInfoDao;
    }

    @PostMapping
    public ResponseEntity<PayrollInfo> createPayrollInfo(@RequestBody PayrollInfo payrollInfo) {
        try {
            PayrollInfo savedPayrollInfo = payrollInfoDao.savePayrollInfo(payrollInfo);
            return new ResponseEntity<>(savedPayrollInfo, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<PayrollInfo> getPayrollInfoById(@PathVariable int id) {
        Optional<PayrollInfo> payrollInfoOpt = payrollInfoDao.getPayrollInfoById(id);
        return payrollInfoOpt.map(ResponseEntity::ok)
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping
    public ResponseEntity<List<PayrollInfo>> getAllPayrollInfos() {
        List<PayrollInfo> payrollInfos = payrollInfoDao.getAllPayrollInfos();
        return new ResponseEntity<>(payrollInfos, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<PayrollInfo> updatePayrollInfo(@PathVariable int id, @RequestBody PayrollInfo updatedPayrollInfo) {
        PayrollInfo existingPayrollInfo = payrollInfoDao.updatePayrollInfo(id, updatedPayrollInfo);
        if (existingPayrollInfo != null) {
            return new ResponseEntity<>(existingPayrollInfo, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePayrollInfo(@PathVariable int id) {
        if (payrollInfoDao.payrollInfoExists(id)) {
            payrollInfoDao.deletePayrollInfo(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
