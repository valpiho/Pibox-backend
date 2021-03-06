package com.pibox.core.service;

import com.pibox.core.repository.DepartmentRepository;
import com.pibox.core.repository.GroupRepository;
import com.pibox.core.domain.model.Department;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final GroupRepository groupRepository;

    public DepartmentService(DepartmentRepository departmentRepository,
                             GroupRepository groupRepository) {
        this.departmentRepository = departmentRepository;
        this.groupRepository = groupRepository;
    }

    public List<Department> findAllActiveDepartments(UUID id) {
        return departmentRepository.findAllByGroup_GroupId(id);
    }

    public Department createNewDepartment(String title, String description,
                                          String country, String city, boolean isPublic, UUID groupId) {
        Department department = new Department();

        department.setTitle(title);
        department.setDescription(description);
        department.setCountry(country);
        department.setCity(city);
        department.setCreatedAt(new Date());
        department.setPublic(isPublic);
        department.setActive(true);
        department.setGroup(groupRepository.findGroupByGroupId(groupId));

        departmentRepository.save(department);
        return department;
    }

    public Department findDepartmentByGroupId(UUID departmentId) {
        return departmentRepository.findByDepartmentId(departmentId);
    }
}
