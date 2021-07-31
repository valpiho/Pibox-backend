package com.pibox.core.controller;

import com.pibox.core.domain.dto.DepartmentDto;
import com.pibox.core.domain.dto.NewDepartmentDto;
import com.pibox.core.domain.model.Department;
import com.pibox.core.mapper.DepartmentMapper;
import com.pibox.core.service.DepartmentService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("api/v1/groups/{groupId}/departments")
public class DepartmentController {

    private final DepartmentService departmentService;
    private final DepartmentMapper departmentMapper;

    public DepartmentController(DepartmentService departmentService,
                                DepartmentMapper departmentMapper) {
        this.departmentService = departmentService;
        this.departmentMapper = departmentMapper;
    }

    @GetMapping
    public ResponseEntity<List<DepartmentDto>> getAllActiveDepartments(@PathVariable("groupId") UUID groupId) {
        List<Department> departments = departmentService.findAllActiveDepartments(groupId);
        List<DepartmentDto> departmentsDtoList = departments.stream().map(departmentMapper::toDepartmentDto).collect(Collectors.toList());
        return new ResponseEntity<>(departmentsDtoList, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<DepartmentDto> createNewDepartment(@PathVariable("groupId") UUID groupId,
                                                             @RequestBody NewDepartmentDto department) {
        Department newDepartment = departmentService.createNewDepartment(department.getTitle(),
                department.getDescription(), department.getCountry(), department.getCity(),
                department.isPublic(), groupId);
        return new ResponseEntity<>(departmentMapper.toDepartmentDto(newDepartment), HttpStatus.OK);
    }

    @GetMapping("/{departmentId}")
    public ResponseEntity<DepartmentDto> getDepartment(@PathVariable("departmentId") UUID departmentId){
        Department department = departmentService.findDepartmentByGroupId(departmentId);
        return new ResponseEntity<>(departmentMapper.toDepartmentDto(department), HttpStatus.OK);
    }
}
