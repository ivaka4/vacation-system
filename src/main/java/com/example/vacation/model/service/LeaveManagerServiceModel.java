package com.example.vacation.model.service;

import com.example.vacation.model.entity.Employee;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.dao.DataAccessException;

import javax.persistence.Column;
import javax.persistence.ManyToOne;
import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class LeaveManagerServiceModel {

    private Long id;

    private Date fromDate;

    private Date toDate;

    private String leaveType;

    private String reason;

    private int duration;

    private boolean acceptRejectFlag;

    private boolean active;

    private Employee user;
}
