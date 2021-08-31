package com.example.vacation.model.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.NotEmpty;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class LeaveEntity extends BaseEntity{

    @Column(name = "from_date")
    private Date fromDate;

    @Column(name = "to_date")
    private Date toDate;

    @Column(name = "leave_type")
    private String leaveType;

    @Column(name = "reason")
    private String reason;

    @Column(name = "duration")
    private int duration;

    @Column(name = "accept_reject_flag")
    private boolean acceptRejectFlag;

    @Column(name = "active")
    private boolean active;

    @ManyToOne
    private Employee user;

}
