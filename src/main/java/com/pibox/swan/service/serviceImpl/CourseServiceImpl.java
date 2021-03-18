package com.pibox.swan.service.serviceImpl;

import com.pibox.swan.domain.model.Course;
import com.pibox.swan.repository.CourseRepository;
import com.pibox.swan.repository.DepartmentRepository;
import com.pibox.swan.repository.GroupRepository;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@Transactional
public class CourseServiceImpl implements CourseService {

    private final GroupRepository groupRepository;
    private final DepartmentRepository departmentRepository;
    private final CourseRepository courseRepository;

    public CourseServiceImpl(GroupRepository groupRepository, DepartmentRepository departmentRepository, CourseRepository courseRepository) {
        this.groupRepository = groupRepository;
        this.departmentRepository = departmentRepository;
        this.courseRepository = courseRepository;
    }

    @Override
    public List<Course> getAllCoursesByDepartment(String departmentId) {
        return courseRepository.findAllByDepartment_DepartmentId(departmentId);
    }

    @Override
    public Course createNewCourse(String title, String description, boolean isPublic, String groupId, String departmentId) {
        Course course = new Course();

        course.setCourseId(generateCourseId());
        course.setTitle(title);
        course.setDescription(description);
        course.setPublic(isPublic);
        course.setGroup(groupRepository.findGroupByGroupId(groupId));
        course.setDepartment(departmentRepository.findByDepartmentId(departmentId));

        courseRepository.save(course);
        return course;
    }

    @Override
    public Course getCourse(String courseId) {
        return courseRepository.findCourseByCourseId(courseId);
    }

    private String generateCourseId() {
        return RandomStringUtils.randomNumeric(10);
    }
}
