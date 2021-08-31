package com.example.vacation.model.binding;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class LeaveManagerBindingModel {

    private Date fromDate;

    private Date toDate;

    private String leaveType;

    private String reason;


}
