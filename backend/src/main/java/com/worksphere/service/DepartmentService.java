package com.worksphere.service;

import com.worksphere.api.dto.Department;
import com.worksphere.api.dto.DepartmentRequest;
import com.worksphere.exception.ResourceNotFoundException;
import com.worksphere.mapper.DepartmentMapper;
import com.worksphere.repository.DepartmentRepository;
import com.worksphere.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final EmployeeRepository employeeRepository;
    private final DepartmentMapper departmentMapper;

    @Transactional(readOnly = true)
    public List<Department> getAllDepartments() {
        return departmentRepository.findAll().stream()
                .map(departmentMapper::toDto)
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public Department getDepartmentById(Long id) {
        return departmentRepository.findById(id)
                .map(departmentMapper::toDto)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
    }

    @Transactional
    public Department createDepartment(DepartmentRequest request) {
        com.worksphere.entity.Department department = departmentMapper.toEntity(request);
        resolveRelationships(request, department);
        return departmentMapper.toDto(departmentRepository.save(department));
    }

    @Transactional
    public Department updateDepartment(Long id, DepartmentRequest request) {
        com.worksphere.entity.Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Department not found with id " + id));
        departmentMapper.updateEntity(request, department);
        resolveRelationships(request, department);
        return departmentMapper.toDto(departmentRepository.save(department));
    }

    private void resolveRelationships(DepartmentRequest request, com.worksphere.entity.Department department) {
        if (request.getHeadId() != null) {
            department.setHead(employeeRepository.findById(request.getHeadId())
                    .orElseThrow(() -> new ResourceNotFoundException("Employee not found with id " + request.getHeadId())));
        } else {
            department.setHead(null);
        }
    }
}
