package com.emp.employee.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.emp.employee.model.Employee;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
    Employee findByEmployeeId(String employeeId);

}
