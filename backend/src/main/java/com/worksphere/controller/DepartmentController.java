package com.worksphere.controller;

import com.worksphere.api.DepartmentsApi;
import com.worksphere.api.dto.Department;
import com.worksphere.api.dto.DepartmentRequest;
import com.worksphere.service.DepartmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class DepartmentController implements DepartmentsApi {

    private final DepartmentService departmentService;

    @Override
    public ResponseEntity<Department> createDepartment(DepartmentRequest departmentRequest) {
        return new ResponseEntity<>(departmentService.createDepartment(departmentRequest), HttpStatus.CREATED);
    }

    @Override
    public ResponseEntity<Department> getDepartment(Long id) {
        return ResponseEntity.ok(departmentService.getDepartmentById(id));
    }

    @Override
    public ResponseEntity<List<Department>> listDepartments() {
        return ResponseEntity.ok(departmentService.getAllDepartments());
    }

    @Override
    public ResponseEntity<Department> updateDepartment(Long id, DepartmentRequest departmentRequest) {
        return ResponseEntity.ok(departmentService.updateDepartment(id, departmentRequest));
    }
}
