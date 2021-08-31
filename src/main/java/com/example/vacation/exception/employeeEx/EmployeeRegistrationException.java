package com.example.vacation.exception.employeeEx;

import com.example.vacation.exception.CustomBaseException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Employee registration exception!")
public class EmployeeRegistrationException extends CustomBaseException {

    public EmployeeRegistrationException(String message) {
        super(message);
    }
}
