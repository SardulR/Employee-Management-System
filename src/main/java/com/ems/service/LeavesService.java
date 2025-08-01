package com.ems.service;

import com.ems.dto.request.LeaveRequestDto;
import com.ems.dto.response.LeaveResponseDto;
import com.ems.entity.Leaves;
import com.ems.enums.Status;
import com.ems.mapper.ModelMapper;
import com.ems.repository.LeavesRepo;
import com.ems.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LeavesService {

    @Autowired
    private LeavesRepo leavesRepo;

    @Autowired
    private UserRepo userRepo;

    // CREATE LEAVE
    public Leaves createLeave(LeaveRequestDto leaveRequest, String username) {
        Leaves leave = ModelMapper.toLeave(leaveRequest);
        leave.setStatus(Status.PENDING);
        leave.setUsername(username);
        leave.setUser(userRepo.findByUsername(username));
        return leavesRepo.save(leave);
    }

    // GET ALL LEAVES

    public List<LeaveResponseDto> getAllLeaves() {
        List<Leaves> leaves = leavesRepo.findAll();
        return ModelMapper.toLeaveResponseDto(leaves);
    }

    // GET LEAVES BY USERNAME
    public List<LeaveResponseDto> getLeavesByUsername(String username) {
        List<Leaves> leaves = leavesRepo.findByUsername(username);
        return ModelMapper.toLeaveResponseDto(leaves);
    }


    // APROVE LEAVE
    public Leaves approveLeave(String leaveId) {
        Optional<Leaves> leave = leavesRepo.findById(leaveId);
        if(leave.isPresent()) {
            leave.get().setStatus(Status.APPROVED);
            Leaves saved = leavesRepo.save(leave.get());
            return saved;
        }
       throw new RuntimeException("Leave not found");
    }

    // REJECT LEAVE
    public Leaves rejectLeave(String leaveId) {
        Optional<Leaves> leave = leavesRepo.findById(leaveId);
        if(leave.isPresent()) {
            leave.get().setStatus(Status.REJECTED);
            Leaves saved = leavesRepo.save(leave.get());
            return saved;
        }
        throw new RuntimeException("Leave not found");
    }
}
