package com.example.vacation.repository;

import com.example.vacation.model.entity.LeaveEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LeaveRepository extends JpaRepository<LeaveEntity ,Long> {
    @Query(nativeQuery = true, value = "select array_to_json(array_agg(row_to_json(t))) from (select employee_name||' on leave' as title,to_char(from_date,'YYYY-MM-DD') as start,to_char(to_date,'YYYY-MM-DD') as end from leave_entity) as t;")
    public Object getAllLeavesAsJsonArray();

    @Query(nativeQuery = true, value = "select * from leave_entity where active=true")
    public List<LeaveEntity> getAllActiveLeaves();

    @Query(nativeQuery = true, value = "select * from leave_entity where user_id=? order by id desc")
    public List<LeaveEntity> getAllLeavesOfUser(Long id);
}
