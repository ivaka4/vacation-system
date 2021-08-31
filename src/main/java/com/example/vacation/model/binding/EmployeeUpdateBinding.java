package com.example.vacation.model.binding;

import com.example.vacation.util.annotation.UniqueEmail;
import com.example.vacation.util.annotation.UniqueUsername;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeUpdateBinding {


    @NotBlank
    @Length(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    private String username;

    @Length(min = 5, max = 35, message = "Email must be at least 5 characters")
    @Pattern(regexp = "^[a-zA-Z0-9.!#$%&'*+\\/=?^_`{|}~-]+@[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?" +
            "(?:\\.[a-zA-Z0-9](?:[a-zA-Z0-9-]{0,61}[a-zA-Z0-9])?)*$", message = "Email is not valid")
    private String email;

    @NotBlank
    @Length(min = 3, max = 20, message = "First name must be between 3 and 20 characters")
    private String firstName;

    @NotBlank
    @Length(min = 3, max = 20, message = "Last name must be between 3 and 20 characters")
    private String lastName;

    @Pattern(regexp = "[0-9]*")
    @NotBlank
    private String phone;

    @NotBlank
    private String role;
}
