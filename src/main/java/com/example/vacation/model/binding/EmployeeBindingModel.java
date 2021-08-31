package com.example.vacation.model.binding;

import com.example.vacation.model.service.AuthorityServiceModel;
import com.example.vacation.util.annotation.UniqueEmail;
import com.example.vacation.util.annotation.UniqueUsername;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class EmployeeBindingModel {


    @NotBlank
    @Length(min = 4, max = 30, message = "Username must be between 4 and 30 characters")
    @UniqueUsername
    private String username;
    @NotBlank
    @Length(min = 6, max = 30, message = "Password must be between 6 and 30")
    @Pattern(regexp = "^[a-zA-Z0-9-\\/.^&*_!@%+>)(]+$", message = "Password must contains, at least " +
            "1 special symbol, 1 uppercase," +
            " lowercase letter, and 1 digit")
    private String password;
    @NotBlank
    @Length(min = 6, max = 30, message = "Password must be between 6 and 30")
    @Pattern(regexp = "^[a-zA-Z0-9-\\/.^&*_!@%+>)(]+$", message = "Password must contains, at least " +
            "1 special symbol, 1 uppercase," +
            " lowercase letter, and 1 digit")
    private String confirmPassword;
    @NotBlank
    @UniqueEmail
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


    private Integer phone;


}
