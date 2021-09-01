package com.example.vacation.service.impl;

import com.example.vacation.exception.employeeEx.EmployeeRegistrationException;
import com.example.vacation.model.binding.LeaveManagerBindingModel;
import com.example.vacation.model.entity.Employee;
import com.example.vacation.model.entity.LeaveEntity;
import com.example.vacation.model.service.LeaveManagerServiceModel;
import com.example.vacation.repository.EmployeeRepository;
import com.example.vacation.repository.LeaveNativeSqlRepo;
import com.example.vacation.repository.LeaveRepository;
import com.example.vacation.service.LeaveService;
import com.example.vacation.util.Tools;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LeaveServiceImpl implements LeaveService {
    private final LeaveRepository leaveRepository;
    private final ModelMapper modelMapper;
    private final EmployeeRepository employeeRepository;
    private final LeaveNativeSqlRepo leaveManageNativeRepo;
    private final Tools tools;

    @Autowired
    public LeaveServiceImpl(LeaveRepository leaveRepository, ModelMapper modelMapper, EmployeeRepository employeeRepository, LeaveNativeSqlRepo leaveManageNativeRepo, Tools tools) {
        this.leaveRepository = leaveRepository;
        this.modelMapper = modelMapper;
        this.employeeRepository = employeeRepository;
        this.leaveManageNativeRepo = leaveManageNativeRepo;
        this.tools = tools;
    }

    @Override
    public void applyLeave(LeaveManagerBindingModel leaveManagerServiceModel) {
        Employee employee = this.employeeRepository.findEmployeeByUsername(this.tools.getLoggedUser()).orElse(null);
        if (employee == null) {
            throw new EmployeeRegistrationException("Leave cannot be make. There is not employee");
        }
        LeaveEntity leaveEntity = new LeaveEntity();
        leaveEntity = this.modelMapper.map(leaveManagerServiceModel, LeaveEntity.class);
        int duration = leaveManagerServiceModel.getToDate().getDate() - leaveEntity.getFromDate().getDate();
        leaveEntity.setDuration(duration + 1);
        leaveEntity.setActive(true);
        leaveEntity.setUser(employee);
        this.leaveRepository.saveAndFlush(leaveEntity);

    }

    @Override
    public List<LeaveManagerServiceModel> getAllActiveLeave() {
        return this.modelMapper.map(this.leaveRepository.getAllActiveLeaves(), new TypeToken<List<LeaveManagerServiceModel>>() {
        }.getType());
    }

    @Override
    public List<LeaveManagerServiceModel> getAllLeavesOfEmployee() {
        Employee employee = this.employeeRepository.findEmployeeByUsername(this.tools.getLoggedUser()).orElse(null);
        if (employee == null) {
            throw new EmployeeRegistrationException("There is not employee with this id");
        }
        return this.modelMapper.map(leaveRepository.getAllLeavesOfUser(employee.getId()), new TypeToken<List<LeaveManagerServiceModel>>() {
        }.getType());
    }

    @Override
    public LeaveManagerServiceModel getLeaveById(Long id) {
        return this.modelMapper.map(this.leaveRepository.findById(id).get(), LeaveManagerServiceModel.class);
    }

    @Override
    @Transactional
    public void updateLeaveEntity(LeaveManagerServiceModel leaveManagerServiceModel) {
        this.leaveRepository.saveAndFlush(this.modelMapper.map(leaveManagerServiceModel, LeaveEntity.class));
    }

    @Override
    public List<LeaveManagerServiceModel> getAllLeaves() {
        return this.modelMapper.map(this.leaveRepository.findAll(), new TypeToken<List<LeaveManagerServiceModel>>() {
        }.getType());
    }

    @Override
    public List<LeaveManagerServiceModel> getAllLeavesOnStatus(boolean pending, boolean accepted, boolean rejected) {
        StringBuffer whereQuery = new StringBuffer();
        if (pending)
            whereQuery.append("active=true or ");
        if (accepted)
            whereQuery.append("(active=false and accept_reject_flag=true) or ");
        if (rejected)
            whereQuery.append("(active=false and accept_reject_flag=false) or ");

        whereQuery.append(" 1=0");
        return this.modelMapper.map(this.leaveManageNativeRepo.getAllLeavesOnStatus(whereQuery), new TypeToken<List<LeaveManagerServiceModel>>() {
        }.getType());
    }

    @Override
    public List<LeaveManagerServiceModel> getAllAcceptedLeaves() {
        return this.modelMapper.map(this.leaveRepository.getAllByAcceptRejectFlag(true), new TypeToken<List<LeaveManagerServiceModel>>() {
        }.getType());
    }
}
