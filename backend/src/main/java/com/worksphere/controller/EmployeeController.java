package com.worksphere.controller;

import com.worksphere.api.EmployeesApi;
import com.worksphere.api.dto.Employee;
import com.worksphere.api.dto.EmployeeRequest;
import com.worksphere.service.EmployeeService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class EmployeeController implements EmployeesApi {

    private final EmployeeService employeeService;

    @Override
    public ResponseEntity<Employee> createEmployee(EmployeeRequest employeeRequest) {
        return new ResponseEntity<>(employeeService.createEmployee(employeeRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Void> deleteEmployee(Long id) {
        employeeService.deleteEmployee(id);
        return ResponseEntity.noContent().build();
    }

    @Override
    public ResponseEntity<Employee> getEmployee(Long id) {
        return ResponseEntity.ok(employeeService.getEmployeeById(id));
    }

    @Override
    public ResponseEntity<List<Employee>> listEmployees() {
        return ResponseEntity.ok(employeeService.getAllEmployees());
    }

    @Override
    public ResponseEntity<Employee> updateEmployee(Long id, EmployeeRequest employeeRequest) {
        return ResponseEntity.ok(employeeService.updateEmployee(id, employeeRequest));
    }
}
