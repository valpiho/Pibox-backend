package com.pibox.core.mapper;

import com.pibox.core.domain.dto.DepartmentDto;
import com.pibox.core.domain.model.Department;
import org.mapstruct.Mapper;

@Mapper
public interface DepartmentMapper {

    DepartmentDto toDepartmentDto(Department department);

    Department toDepartment(DepartmentDto departmentDto);
}
