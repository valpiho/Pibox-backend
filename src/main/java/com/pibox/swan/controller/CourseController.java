package com.pibox.swan.controller;

import com.pibox.swan.domain.model.Course;
import com.pibox.swan.service.CourseService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/groups/{groupId}/courses")
public class CourseController {

    private final CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    @GetMapping("/")
    public List<Course> getAllCourses(@PathVariable("departmentId") String departmentId) {
        return courseService.getAllCoursesByDepartment(departmentId);
    }

    @PostMapping("/create")
    public ResponseEntity<Course> createNewCourse(@PathVariable("groupId") String groupId,
                                                      @PathVariable("departmentId") String departmentId,
                                                      @RequestBody Course course) {
        Course newCourse = courseService.createNewCourse(course.getTitle(), course.getDescription(), course.isPublic(), groupId, departmentId);
        return new ResponseEntity<>(newCourse, HttpStatus.OK);
    }

    @GetMapping("/{courseId}")
    public Course getCourse(@PathVariable("courseId") String courseId){
        return courseService.getCourse(courseId);
    }
}
