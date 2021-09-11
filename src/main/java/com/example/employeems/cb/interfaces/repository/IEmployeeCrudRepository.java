package com.example.employeems.cb.interfaces.repository;

import com.example.employeems.EmployeeDTO;
import org.springframework.data.couchbase.repository.CouchbaseRepository;

import java.util.List;

public interface IEmployeeCrudRepository extends CouchbaseRepository <EmployeeDTO, String> {
    List<EmployeeDTO> findAll();
    EmployeeDTO save(EmployeeDTO employeeDTO);
}
