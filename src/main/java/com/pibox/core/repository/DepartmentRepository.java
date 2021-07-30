package com.pibox.core.repository;

import com.pibox.core.domain.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {

    List<Department> findAllByGroup_Id(UUID id);

    Department findByDepartmentId(String departmentId);
}
