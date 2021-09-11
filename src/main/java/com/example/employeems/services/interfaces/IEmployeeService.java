package com.example.employeems.services.interfaces;

import com.example.employeems.Employee;
import com.example.employeems.EmployeeDTO;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {
    List<EmployeeDTO> getEmployee();
    boolean addEmployee(List<EmployeeDTO> employee);
}
