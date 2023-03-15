package com.cst438.controllers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.HashMap;
import java.time.LocalDateTime;


import org.springframework.beans.factory.annotation.Autowired;
import com.cst438.domain.*;


@RestController
public class InstructorController {
	
    @Autowired
    AssignmentRepository assignmentRepository;

    @Autowired
    AssignmentGradeRepository assignmentGradeRepository;

    @Autowired
    CourseRepository courseRepository;
    
	//  As an instructor for a course , I can add a new assignment for my course.  
	//The assignment has a name and a due date.
	@PostMapping("/api/courses/{courseId}/assignments")
	public ResponseEntity<Object> addAssignment(@PathVariable("courseId") Long courseId, @RequestBody Assignment assignment) {
	    Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
	    course.addAssignment(assignment);
	    courseRepository.save(course);
	    return ResponseEntity.status(HttpStatus.CREATED).build();
	}

	
	
	
	
	//As an instructor, I can change the name of the assignment for my course.
	@PutMapping("/api/courses/{courseId}/assignments/{assignmentId}")
	public ResponseEntity<Object> updateAssignment(@PathVariable("courseId") Long courseId, @PathVariable("assignmentId") Long assignmentId, @RequestBody Assignment assignment) {
	    Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
	    Assignment existingAssignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new ResourceNotFoundException("Assignment", "id", assignmentId));
	    existingAssignment.setName(assignment.getName());
	    existingAssignment.setDueDate(assignment.getDueDate());
	    assignmentRepository.save(existingAssignment);
	    return ResponseEntity.ok().build();
	}

	
	
	//As an instructor, 
	//I can delete an assignment  for my course (only if there are no grades for the assignment).
	@DeleteMapping("/api/courses/{courseId}/assignments/{assignmentId}")
	public ResponseEntity<Object> deleteAssignment(@PathVariable("courseId") Long courseId, @PathVariable("assignmentId") Long assignmentId) {
	    Course course = courseRepository.findById(courseId).orElseThrow(() -> new ResourceNotFoundException("Course", "id", courseId));
	    Assignment assignment = assignmentRepository.findById(assignmentId).orElseThrow(() -> new ResourceNotFoundException("Assignment", "id", assignmentId));
	    if (assignment.getGrades().isEmpty()) {
	        course.removeAssignment(assignment);
	        courseRepository.save(course);
	        assignmentRepository.delete(assignment);
	        return ResponseEntity.noContent().build();
	    } else {
	        return ResponseEntity.status(HttpStatus.CONFLICT).body("Cannot delete an assignment with existing grades.");
	    }
	}


}