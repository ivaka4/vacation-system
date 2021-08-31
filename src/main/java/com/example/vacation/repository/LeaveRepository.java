package com.example.vacation.repository;

import com.example.vacation.model.entity.LeaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveEntity ,Long> {
}
