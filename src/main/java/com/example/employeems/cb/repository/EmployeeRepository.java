package com.example.employeems.cb.repository;

import com.example.employeems.Employee;
import com.example.employeems.EmployeeDTO;
import com.example.employeems.cb.interfaces.repository.IEmployeeCrudRepository;
import com.example.employeems.cb.interfaces.repository.IEmployeeRepository;
import org.springframework.stereotype.Repository;

import javax.inject.Inject;
import java.util.List;
import java.util.Optional;

@Repository
public class EmployeeRepository implements IEmployeeRepository {
    @Inject
    private IEmployeeCrudRepository employeeCrudRepository;

    @Override
    public Optional<List<EmployeeDTO>> getEmployee() {
        Optional<List<EmployeeDTO>> optionalEmployeeList = Optional.ofNullable(employeeCrudRepository.findAll());
        return optionalEmployeeList;
    }

    @Override
    public void addEmployee(EmployeeDTO employee) {
        employeeCrudRepository.save(employee);
    }
}
