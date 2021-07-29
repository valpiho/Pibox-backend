package com.pibox.core.repository;

import com.pibox.core.domain.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    List<Course> findAllByDepartment_DepartmentId(String departmentId);

    Course findCourseByCourseId(String courseId);
}
