package com.pibox.core.controller;

import com.pibox.core.domain.model.Department;
import com.pibox.core.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/groups/{id}/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAllActiveDepartments(@PathVariable("id") UUID id) {
        return departmentService.findAllActiveDepartments(id);
    }

    @PostMapping
    public ResponseEntity<Department> createNewDepartment(@PathVariable("id") UUID id,
                                                          @RequestBody Department department) {
        Department newDepartment = departmentService.createNewDepartment(department.getTitle(),
                department.getDescription(), department.getCountry(), department.getCity(),
                department.isPublic(), id);
        return new ResponseEntity<>(newDepartment, HttpStatus.OK);
    }

    @GetMapping("/{departmentId}")
    public Department getDepartment(@PathVariable("departmentId") String departmentId){
        return departmentService.findDepartmentByGroupId(departmentId);
    }
}
