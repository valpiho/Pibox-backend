package com.pibox.swan.controller;

import com.pibox.swan.domain.model.Department;
import com.pibox.swan.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/v1/groups/{groupId}/departments")
public class DepartmentController {

    private final DepartmentService departmentService;

    public DepartmentController(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    @GetMapping
    public List<Department> getAllActiveDepartments(@PathVariable("groupId") String groupId) {
        return departmentService.findAllActiveDepartments(groupId);
    }

    @PostMapping
    public ResponseEntity<Department> createNewDepartment(@PathVariable("groupId") String groupId,
                                                          @RequestBody Department department) {
        Department newDepartment = departmentService.createNewDepartment(department.getTitle(),
                department.getDescription(), department.getCountry(), department.getCity(),
                department.isPublic(), groupId);
        return new ResponseEntity<>(newDepartment, HttpStatus.OK);
    }

    @GetMapping("/{departmentId}")
    public Department getDepartment(@PathVariable("departmentId") String departmentId){
        return departmentService.findDepartmentByGroupId(departmentId);
    }
}
