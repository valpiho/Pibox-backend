package com.pibox.swan.service;

import com.pibox.swan.domain.model.Course;

import java.util.List;

public interface CourseService {
    
    List<Course> getAllCoursesByDepartment(String departmentId);

    Course createNewCourse(String title, String description, boolean isPublic, String groupId, String departmentId);

    Course getCourse(String courseId);
}
