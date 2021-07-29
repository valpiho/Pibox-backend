package com.pibox.core.service;

import com.pibox.core.domain.model.Department;

import java.util.List;

public interface DepartmentService {
    
    List<Department> findAllActiveDepartments(String groupId);

    Department createNewDepartment(String title, String description,
                                   String country, String city, boolean isPublic, String groupId);

    Department findDepartmentByGroupId(String departmentId);
}
