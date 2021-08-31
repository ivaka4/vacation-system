package com.example.vacation.service;

import com.example.vacation.model.binding.LeaveManagerBindingModel;
import com.example.vacation.model.service.LeaveManagerServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface LeaveService{

    void applyLeave(LeaveManagerBindingModel leaveManagerServiceModel);

    List<LeaveManagerServiceModel> getAllActiveLeave();

    List<LeaveManagerServiceModel> getAllLeavesOfEmployee(Long username);


    LeaveManagerServiceModel getLeaveById(Long id);

    void updateLeaveEntity(LeaveManagerServiceModel leaveManagerServiceModel);
}
