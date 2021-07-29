package com.pibox.core.service.serviceImpl;

import com.pibox.core.repository.DepartmentRepository;
import com.pibox.core.repository.GroupRepository;
import com.pibox.core.domain.model.Department;
import com.pibox.core.service.DepartmentService;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Date;
import java.util.List;

@Service
@Transactional
public class DepartmentServiceImpl implements DepartmentService {

    private final DepartmentRepository departmentRepository;
    private final GroupRepository groupRepository;

    public DepartmentServiceImpl(DepartmentRepository departmentRepository,
                                 GroupRepository groupRepository) {
        this.departmentRepository = departmentRepository;
        this.groupRepository = groupRepository;
    }


    @Override
    public List<Department> findAllActiveDepartments(String groupId) {
        return departmentRepository.findAllByGroup_GroupId(groupId);
    }

    @Override
    public Department createNewDepartment(String title, String description,
                                          String country, String city, boolean isPublic, String groupId) {
        Department department = new Department();

        department.setDepartmentId(generateDepartmentId());
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

    @Override
    public Department findDepartmentByGroupId(String departmentId) {
        return departmentRepository.findByDepartmentId(departmentId);
    }

    private String generateDepartmentId() {
        return RandomStringUtils.randomNumeric(10);
    }
}
