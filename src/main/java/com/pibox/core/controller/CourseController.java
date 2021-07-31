package com.pibox.core.controller;

import com.pibox.core.domain.model.Course;
import com.pibox.core.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("api/v1/groups/{id}/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping
    public List<Course> getAllCourses(@PathVariable("departmentId") String departmentId) {
        return courseService.getAllCoursesByDepartment(departmentId);
    }

    @PostMapping
    public ResponseEntity<Course> createNewCourse(@PathVariable("id") UUID id,
                                                      @PathVariable("departmentId") UUID departmentId,
                                                      @RequestBody Course course) {
        Course newCourse = courseService.createNewCourse(course.getTitle(), course.getDescription(), course.isPublic(), id, departmentId);
        return new ResponseEntity<>(newCourse, HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable("courseId") String courseId){
        return courseService.getCourse(courseId);
    }
}
