package com.example.vacation.service;

import com.example.vacation.model.binding.EmployeeBindingModel;
import com.example.vacation.model.binding.EmployeeUpdateBinding;
import com.example.vacation.model.entity.Employee;
import com.example.vacation.model.service.EmployeeServiceModel;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.List;

public interface EmployeeService extends UserDetailsService {

    EmployeeServiceModel findEmployeeById(Long id);

    EmployeeServiceModel findUserByEmail(String email);
    EmployeeServiceModel findUserByUsername(String username);

    void saveUser(EmployeeBindingModel employeeBindingModel);

    boolean emailExist(String email);

    boolean userExists(String username);

    List<EmployeeServiceModel> getEmployees();

    void deleteEmployee(Long id);

    void blockEmployee(Long id);

    void unBlockEmployee(Long id);

    void updateUser(EmployeeUpdateBinding employeeUpdateBinding);

}
