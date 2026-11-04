package com.worksphere.service;

import com.worksphere.api.dto.Employee;
import com.worksphere.api.dto.EmployeeRequest;
import com.worksphere.exception.ResourceNotFoundException;
import com.worksphere.mapper.EmployeeMapper;
import com.worksphere.repository.DepartmentRepository;
import com.worksphere.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;
    private final EmployeeMapper employeeMapper;

    @Transactional(readOnly = true)
    public List<Employee> getAllEmployees() {
        return employeeRepository.findAll().stream()
                .map(employeeMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeById(Long id) {
        return employeeRepository.findById(id)
                .map(employeeMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
    }

    @Transactional
    public Employee createEmployee(EmployeeRequest request) {
        com.worksphere.entity.Employee employee = employeeMapper.toEntity(request);
        resolveRelationships(request, employee);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Transactional
    public Employee updateEmployee(Long id, EmployeeRequest request) {
        com.worksphere.entity.Employee employee = employeeRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + id));
        employeeMapper.updateEntity(request, employee);
        resolveRelationships(request, employee);
        return employeeMapper.toDto(employeeRepository.save(employee));
    }

    @Transactional
    public void deleteEmployee(Long id) {
        if (!employeeRepository.existsById(id)) {
            throw new ResourceNotFoundException("Employee not found with id " + id);
        }
        employeeRepository.deleteById(id);
    }

    private void resolveRelationships(EmployeeRequest request, com.worksphere.entity.Employee employee) {
        if (request.getDepartmentId() != null) {
            employee.setDepartment(departmentRepository.findById(request.getDepartmentId())
                    .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + request.getDepartmentId())));
        } else {
            employee.setDepartment(null);
        }

        if (request.getManagerId() != null) {
            employee.setManager(employeeRepository.findById(request.getManagerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Manager not found with id " + request.getManagerId())));
        } else {
            employee.setManager(null);
        }
    }
}
