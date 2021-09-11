package com.example.employeems.services;

import com.example.employeems.Employee;
import com.example.employeems.EmployeeDTO;
import com.example.employeems.cb.interfaces.repository.IEmployeeRepository;
import com.example.employeems.exceptions.BadRequestException;
import com.example.employeems.exceptions.NotFoundException;
import com.example.employeems.kafka.client.interfaces.IEmployeeNotification;
import com.example.employeems.services.interfaces.IEmployeeService;
import org.springframework.stereotype.Service;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class EmployeeServices implements IEmployeeService {
    @Inject
    private IEmployeeRepository employeeRepository;

    @Inject
    private IEmployeeNotification employeeNotification;

    @Override
    public List<EmployeeDTO> getEmployee() {
        Optional<List<EmployeeDTO>> optionalEmployeeList = employeeRepository.getEmployee();
        if(!optionalEmployeeList.isPresent()){
            throw  new NotFoundException("No Employee in DB");
        }
        return optionalEmployeeList.orElse(new ArrayList<>());
    }

    @Override
    public boolean addEmployee(List<EmployeeDTO> employee) {
        if(employee.isEmpty()){
            throw new BadRequestException("Empty Employee to save");
        }
        employee.parallelStream()
                .forEach(employeeRecord -> {
                    employeeRepository.addEmployee(employeeRecord);
                });
        employeeNotification.publish(employee);
        return true;
    }

}
