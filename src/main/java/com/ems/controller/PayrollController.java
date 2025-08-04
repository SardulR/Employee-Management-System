package com.ems.controller;

import com.ems.dto.request.PayrollRequestDto;
import com.ems.dto.request.PayrollUpdateRequest;
import com.ems.entity.Payroll;
import com.ems.entity.UserPayrolls;
import com.ems.service.PayrollService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/payroll")
public class PayrollController {

    @Autowired
    private PayrollService payrollService;


    // GENERATE PAYROLL    ACCESS: ADMIN

    @PostMapping("/generate")
    public ResponseEntity<?> generatePayroll(@Valid  @RequestBody PayrollRequestDto payroll){
        Payroll generated = payrollService.generatePayroll(payroll);
        if(generated!=null){
            UserPayrolls res = payrollService.getPayrollByMonth(payroll.getUsername(), payroll.getMonth());
            return new ResponseEntity<>(res, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(Map.of("message", "Generated Failed"), HttpStatus.CREATED);
    }

    // GET PAYROLL  ACCESS: ALL USER

    @GetMapping("/user/{username}")
    public ResponseEntity<?> getPayrollByUser(@PathVariable String username){
        Payroll payrollByUser = payrollService.getPayrollByUser(username);
        return new ResponseEntity<>(payrollByUser, HttpStatus.OK);
    }

    // GET PAYROLL BY MONTH   ACCESS: ALL USER
    @GetMapping("/{username}/{month}")
    public ResponseEntity<?> getPayrollByMonth(@PathVariable String username, @PathVariable String month){
        UserPayrolls payroll = payrollService.getPayrollByMonth(username, month);
        return new ResponseEntity<>(payroll, HttpStatus.OK);
    }

    // GET ALL PAYROLL  ACCESS: ADMIN
    @GetMapping("/all")
    public ResponseEntity<?> getAllPayrolls(){
        List<Payroll> payrolls =  payrollService.getAllPayrolls();
        return new ResponseEntity<>(payrolls, HttpStatus.OK);
    }

    // UPDATE PAYROLL STATUS  ACCESS: ADMIN
    @PutMapping("/update-status")
    public ResponseEntity<?> updatePayrollStatus(@Valid @RequestBody PayrollUpdateRequest payroll){

        String res = payrollService.updateStatus(payroll.getId(), payroll.getStatus());
        return new ResponseEntity<>(Map.of("message", res), HttpStatus.OK);
    }
}
