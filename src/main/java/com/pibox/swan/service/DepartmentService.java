package com.pibox.swan.service;

import com.pibox.swan.domain.model.Department;
import com.pibox.swan.domain.model.Group;

import java.util.List;

public interface DepartmentService {
    
    List<Department> findAllActiveDepartments(String groupId);

    Department createNewDepartment(String title, String description,
                                   String country, String city, boolean isPublic, String groupId);

    Department findDepartmentByGroupId(String departmentId);
}
