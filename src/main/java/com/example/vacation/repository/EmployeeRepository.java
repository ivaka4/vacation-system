package com.example.vacation.repository;

import com.example.vacation.model.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByUsername(String username);

    Employee findUserByEmail(String email);

    Optional<Employee> findEmployeeByUsername(String name);

    List<Employee> findAllByOrderById();

    @Transactional
    @Modifying
    @Query(value = "update employee set active=false where id=?", nativeQuery = true)
    public void blockUser(Long id);

    @Transactional
    @Modifying
    @Query(value = "update employee set active=true where id=?", nativeQuery = true)
    public void unBlockUser(Long id);
}
