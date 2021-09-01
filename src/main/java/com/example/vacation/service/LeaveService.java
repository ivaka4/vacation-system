package com.example.vacation.service;

import com.example.vacation.model.binding.LeaveManagerBindingModel;
import com.example.vacation.model.service.LeaveManagerServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface LeaveService{

    void applyLeave(LeaveManagerBindingModel leaveManagerServiceModel);

    List<LeaveManagerServiceModel> getAllActiveLeave();

    List<LeaveManagerServiceModel> getAllLeavesOfEmployee();


    LeaveManagerServiceModel getLeaveById(Long id);

    void updateLeaveEntity(LeaveManagerServiceModel leaveManagerServiceModel);

    List<LeaveManagerServiceModel> getAllLeaves();

    List<LeaveManagerServiceModel> getAllLeavesOnStatus(boolean pending, boolean accepted, boolean rejected);

    List<LeaveManagerServiceModel> getAllAcceptedLeaves();
}
