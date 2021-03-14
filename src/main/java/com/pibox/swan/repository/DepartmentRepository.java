package com.pibox.swan.repository;

import com.pibox.swan.domain.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long> {

    List<Department> findAllByGroup_GroupId(String groupId);

    Department findByDepartmentId(String departmentId);
}
