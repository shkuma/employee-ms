package com.example.employeems.kafka.client.interfaces;

import com.example.employeems.Employee;
import com.example.employeems.EmployeeDTO;

import java.util.List;

public interface IEmployeeNotification {
    void publish(List<EmployeeDTO> employee);
}
