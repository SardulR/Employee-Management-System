package com.ems.service;


import com.ems.dto.request.PayrollRequestDto;
import com.ems.dto.response.UserResponseDto;
import com.ems.entity.Attendance;
import com.ems.entity.Payroll;
import com.ems.entity.UserPayrolls;
import com.ems.enums.PayRollStatus;
import com.ems.repository.PayrollRepo;
import com.ems.repository.UserPayrollsRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class PayrollService {

    @Autowired
    private UserService userService;

    @Autowired
    private AttendanceService attendanceService;

    @Autowired
    private PayrollRepo payrollRepo;

    @Autowired
    private UserPayrollsRepo userPayrollsRepo;



    //GENERATE NEW PAYROLL

    public Payroll generatePayroll(PayrollRequestDto payroll) {

        Payroll existingPayroll = payrollRepo.findByUsername(payroll.getUsername());

        if(existingPayroll != null){
            int month = Integer.valueOf(payroll.getMonth().substring(0,2));
            int year = Integer.valueOf(payroll.getMonth().substring(2).trim());
            UserResponseDto user = userService.getUserByUsername(payroll.getUsername());
            List<Attendance> attendance = attendanceService.findAttendanceByMonthAndYear(month, year);
            double basicSalaryOfOneDay = (user.getSalary())/26;
            double total_working = (attendance.size()); // +4 for sunday.
            double bonus = 0;

            double deduction = 0;
            if(total_working >26){
                bonus = (total_working-26)*basicSalaryOfOneDay;
            }
            else if(attendance.size() >0){
                deduction = (26-total_working)*basicSalaryOfOneDay;
            }
            double netSallary = basicSalaryOfOneDay*total_working + bonus - deduction;

            UserPayrolls userPayrolls = new UserPayrolls().builder()
                    .basicSalary(user.getSalary())
                    .bonus(bonus)
                    .deductions(deduction)
                    .netSalary(netSallary)
                    .month(payroll.getMonth())
                    .status(PayRollStatus.PENDING)
                    .generatedAt(LocalDate.now())
                    .build();

            List<UserPayrolls> payrollsList = existingPayroll.getPayrolls();
            payrollsList.add(userPayrolls);

            existingPayroll.setPayrolls(payrollsList);
            return payrollRepo.save(existingPayroll);
        }

        Payroll pr = new Payroll();
        List<UserPayrolls> payrollsList = pr.getPayrolls();
        pr.setUsername(payroll.getUsername());
        pr.setPayrolls(payrollsList);

        return payrollRepo.save(pr);
    }


    // GET PAYROLL BY USERNAME
    public Payroll getPayrollByUser(String username){
        return payrollRepo.findByUsername(username);
    }

    // GET PAYROLL OF USER BY MONTH
    public UserPayrolls getPayrollByMonth(String username, String month){
        return userPayrollsRepo.findByMonth(month);
    }

    // GET ALL PAYROLLS
    public List<Payroll> getAllPayrolls(){
        return payrollRepo.findAll();
    }

    // UPDATE STATUS OF A PAYROLL
    public String updateStatus(Long id,PayRollStatus status){
        UserPayrolls userPayrolls = userPayrollsRepo.findById(id).orElseThrow(() -> new IllegalArgumentException("Payroll id not found!"));
        userPayrolls.setStatus(status);
        userPayrollsRepo.save(userPayrolls);
        return "success";
    }
}
