package com.example.vacation.service.impl;

import com.example.vacation.exception.employeeEx.EmployeeRegistrationException;
import com.example.vacation.model.binding.EmployeeBindingModel;
import com.example.vacation.model.binding.EmployeeUpdateBinding;
import com.example.vacation.model.entity.Authority;
import com.example.vacation.model.entity.Employee;
import com.example.vacation.model.entity.UserSecurity;
import com.example.vacation.model.enums.RoleEnum;
import com.example.vacation.model.service.EmployeeServiceModel;
import com.example.vacation.repository.AuthorityRepository;
import com.example.vacation.repository.EmployeeRepository;
import com.example.vacation.service.AuthorityService;
import com.example.vacation.service.EmployeeService;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServiceImpl implements EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final AuthorityService authorityService;
    private final AuthorityRepository authorityRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    @Autowired
    public EmployeeServiceImpl(EmployeeRepository employeeRepository, AuthorityService authorityService, AuthorityRepository authorityRepository, PasswordEncoder passwordEncoder, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.authorityService = authorityService;
        this.authorityRepository = authorityRepository;
        this.passwordEncoder = passwordEncoder;
        this.modelMapper = modelMapper;
    }

    @Override
    public EmployeeServiceModel findEmployeeById(Long id) {
        EmployeeServiceModel employeeService = this.modelMapper.map(this.employeeRepository.getById(id), EmployeeServiceModel.class);
        if (employeeService == null) {
            throw new EmployeeRegistrationException("There is not employee with this id: " + id);
        }
        return employeeService;
    }

    @Override
    public EmployeeServiceModel findUserByEmail(String email) {
        return this.modelMapper.map(this.employeeRepository.findUserByEmail(email), EmployeeServiceModel.class);
    }

    @Override
    public EmployeeServiceModel findUserByUsername(String username) {
        return this.modelMapper.map(this.employeeRepository.findByUsername(username), EmployeeServiceModel.class);
    }

    @Override
    public void saveUser(EmployeeBindingModel employeeBindingModel) {
        Employee employee = this.modelMapper.map(employeeBindingModel, Employee.class);
        if (employee == null) {
            throw new EmployeeRegistrationException("The provided binding is null");
        }
        employee.setRegisterOn(LocalDateTime.now());
        if (this.employeeRepository.count() == 0) {
            authorityService.seedAuthorities();
            employee.setAuthorities(new HashSet<>(this.authorityRepository.findAll()));
            employee.setActive(true);
        } else {
            employee.setAuthorities(new HashSet<>(authorityRepository.findAllByAuthority(RoleEnum.EMPLOYEE)));
            employee.setActive(false);
        }
        employee.setPassword(passwordEncoder.encode(employeeBindingModel.getPassword()));
        try {
            this.employeeRepository.saveAndFlush(employee);
        } catch (Exception ex) {
            throw new EmployeeRegistrationException("Cannot persist entity to database");
        }
    }

    @Override
    public boolean emailExist(String email) {
        return employeeRepository.findUserByEmail(email) == null;
    }

    @Override
    public boolean userExists(String username) {
        return employeeRepository.findByUsername(username) != null;
    }

    @Override
    public List<EmployeeServiceModel> getEmployees() {
        return this.modelMapper.map(this.employeeRepository.findAllByOrderById(),
                new TypeToken<List<EmployeeServiceModel>>() {
                }.getType());
    }

    @Override
    public void deleteEmployee(Long id) {
        Employee employee = this.employeeRepository.getById(id);
        employee.setAuthorities(null);
        this.employeeRepository.delete(employee);
    }

    @Override
    public void blockEmployee(Long id) {
        this.employeeRepository.blockUser(id);
    }

    @Override
    @Transactional
    public void unBlockEmployee(Long id) {
        this.employeeRepository.unBlockUser(id);
    }

    @Override
    public void updateUser(EmployeeUpdateBinding employeeUpdateBinding) {
        Employee employee = this.employeeRepository.findEmployeeByUsername(employeeUpdateBinding.getUsername()).orElse(null);
        if (employee == null) {
            throw new EmployeeRegistrationException("Employee is null");
        }
        employee.setFirstName(employeeUpdateBinding.getFirstName());
        employee.setLastName(employeeUpdateBinding.getLastName());
        employee.setPhone(Integer.parseInt(employeeUpdateBinding.getPhone()));
        employee.setEmail(employeeUpdateBinding.getEmail());
        Authority authority = this.authorityRepository.findByAuthority(RoleEnum.valueOf(employeeUpdateBinding.getRole()));
        if (employeeUpdateBinding.getRole().equals("MANAGER")) {
            if (employee.getAuthorities().contains(this.authorityRepository.findByAuthority(RoleEnum.HR))) {
                employee.getAuthorities().remove(this.authorityRepository.findByAuthority(RoleEnum.HR));
                employee.getAuthorities().add(authority);
            } else {
                employee.getAuthorities().add(authority);
            }
        } else if (employeeUpdateBinding.getRole().equals("HR")) {
            if (employee.getAuthorities().contains(this.authorityRepository.findByAuthority(RoleEnum.MANAGER))) {
                employee.getAuthorities().remove(this.authorityRepository.findByAuthority(RoleEnum.MANAGER));
                employee.getAuthorities().add(authority);
            } else {
                employee.getAuthorities().add(authority);
            }
        } else if (employeeUpdateBinding.getRole().equals("EMPLOYEE")) {
            employee.getAuthorities().clear();
            employee.getAuthorities().add(authority);
        }
        this.employeeRepository.saveAndFlush(employee);

    }

    @Override
    public List<EmployeeServiceModel> getActiveEmployees() {
        return this.modelMapper.map(this.employeeRepository.findAllByActiveTrue(), new TypeToken<List<EmployeeServiceModel>>() {
        }.getType());
    }

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        Optional<Employee> user = this.employeeRepository.findEmployeeByUsername(s);
        user.orElseThrow(() -> new UsernameNotFoundException(s));
        return user.map(UserSecurity::new).get();
    }
}
