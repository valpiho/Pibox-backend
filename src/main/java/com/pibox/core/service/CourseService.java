package com.pibox.core.service;

import com.pibox.core.repository.CourseRepository;
import com.pibox.core.repository.DepartmentRepository;
import com.pibox.core.repository.GroupRepository;
import com.pibox.core.domain.model.Course;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class CourseService {

    private final GroupRepository groupRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;

    public CourseService(GroupRepository groupRepository, DepartmentRepository departmentRepository, CourseRepository courseRepository) {
        this.groupRepository = groupRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
    }

    public List<Course> getAllCoursesByDepartment(String departmentId) {
        return courseRepository.findAllByDepartment_DepartmentId(departmentId);
    }

    public Course createNewCourse(String title, String description, boolean isPublic, UUID id, String departmentId) {
        Course course = new Course();

        course.setCourseId(generateCourseId());
        course.setTitle(title);
        course.setDescription(description);
        course.setPublic(isPublic);
        course.setGroup(groupRepository.findGroupById(id));
        course.setDepartment(departmentRepository.findByDepartmentId(departmentId));

        courseRepository.save(course);
        return course;
    }

    public Course getCourse(String courseId) {
        return courseRepository.findCourseByCourseId(courseId);
    }

    private String generateCourseId() {
        return RandomStringUtils.randomNumeric(10);
    }
}
