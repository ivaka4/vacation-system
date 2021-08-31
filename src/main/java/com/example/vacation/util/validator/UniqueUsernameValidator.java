package com.example.vacation.util.validator;

import com.example.vacation.service.EmployeeService;
import com.example.vacation.util.annotation.UniqueUsername;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor(onConstructor = @__(@Autowired))
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)

public class UniqueUsernameValidator implements
        ConstraintValidator<UniqueUsername,String> {

    EmployeeService employeeService;

    @Override
    public void initialize(UniqueUsername constraintAnnotation) {

    }

    @Override
    public boolean isValid(String username, ConstraintValidatorContext context) {
        return !employeeService.userExists(username);
    }
}