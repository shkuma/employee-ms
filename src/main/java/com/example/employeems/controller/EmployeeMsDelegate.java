package com.example.employeems.controller;

import com.example.employeems.Employee;
import com.example.employeems.EmployeeDTO;
import com.example.employeems.EmployeesApi;
import com.example.employeems.exceptions.BadRequestException;
import com.example.employeems.services.interfaces.IEmployeeService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class EmployeeMsDelegate implements EmployeesApi {

    @Inject
    private HttpServletRequest request;

    @Inject
    private IEmployeeService employeeService;

    @Override
    public ResponseEntity<List<Employee>> getEmployees() {
        List<Employee> employeeList = mapEmployeeDTOListToEmployeeList(employeeService.getEmployee());
        return new ResponseEntity(employeeList, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<Employee>> addEmployeeList(List<Employee> employee) {
        List<EmployeeDTO> employeeDTOList = mapEmployeeListToEmployeeDTOList(employee);
        return new ResponseEntity(employeeService.addEmployee(employeeDTOList) == true ?  employee : new ArrayList<>(), HttpStatus.OK);
    }



    @Override
    public Optional<HttpServletRequest> getRequest() {
        Optional<HttpServletRequest> httpServletRequestOptional = Optional.ofNullable(request);
        return httpServletRequestOptional;
    }


    @Override
    public Optional<String> getAcceptHeader() {
        Optional<String> acceptHeader = getRequest().map(r -> r.getHeader("Accept"));
        return getRequest().map(r -> r.getHeader("Accept"));
    }

    private List<EmployeeDTO> mapEmployeeListToEmployeeDTOList(List<Employee> employeeList){
        List<EmployeeDTO> employeeDTOList = new ArrayList<>();
        for(Employee employee: employeeList){
            EmployeeDTO employeeDTO = mapEmployeeToEmployeeDTO(employee);
            employeeDTOList.add(employeeDTO);
        }
        return employeeDTOList;
    }

    private EmployeeDTO mapEmployeeToEmployeeDTO(Employee employee) {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setEmployeeId(employee.getEmployeeId());
        employeeDTO.setEmployeeName(employee.getEmployeeName());
        employeeDTO.setAddress(employee.getAddress());
        employeeDTO.setMobileNumber(employee.getMobileNumber());
        return employeeDTO;
    }

    private List<Employee> mapEmployeeDTOListToEmployeeList(List<EmployeeDTO> employeeDTOList){
        List<Employee> employeeList = new ArrayList<>();
        for(EmployeeDTO employeeDTO: employeeDTOList){
            Employee employee = mapEmployeeDTOToEmployee(employeeDTO);
            employeeList.add(employee);
        }
        return employeeList;
    }

    private Employee mapEmployeeDTOToEmployee(EmployeeDTO employeeDTO) {
        Employee employee = new Employee();
        employee.setEmployeeId(employeeDTO.getEmployeeId());
        employee.setEmployeeName(employeeDTO.getEmployeeName());
        employee.setAddress(employeeDTO.getAddress());
        employee.setMobileNumber(employeeDTO.getMobileNumber());
        return employee;
    }
}
