package com.example.employeems.cb.interfaces.repository;

import com.example.employeems.Employee;
import com.example.employeems.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface IEmployeeRepository {
    Optional<List<EmployeeDTO>> getEmployee();
    void addEmployee(EmployeeDTO employee);
}
