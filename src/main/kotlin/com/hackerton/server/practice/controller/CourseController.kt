package com.hackerton.server.practice.controller

import com.hackerton.server.practice.dto.CourseDTO
import com.hackerton.server.practice.service.CourseService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/v1/course")
@Validated // AOP 기반 방식 vs ArgumentResolver 방식 ( https://mangkyu.tistory.com/174 )
class CourseController(val courseService: CourseService) {

    @PostMapping
    fun addCourse(@RequestBody @Valid courseDTO: CourseDTO) : ResponseEntity<CourseDTO> = ResponseEntity.status(HttpStatus.CREATED).body(courseService.addCourse(courseDTO))

    @GetMapping
    fun retrieveAllCourses(@RequestParam("course_name", required=false) courseName : String?) : ResponseEntity<List<CourseDTO>> = ResponseEntity.status(HttpStatus.OK).body(courseService.retrieveAllCourses(courseName))

    @PutMapping("/{course_id}")
    fun updateCourse(@RequestBody courseDTO: CourseDTO, @PathVariable("course_id") courseId: Int) : ResponseEntity<CourseDTO> = ResponseEntity.status(HttpStatus.CREATED).body(courseService.updateCourse(courseId, courseDTO))

    @DeleteMapping("/{course_id}")
    fun deleteCourse(@PathVariable("course_id") courseId: Int) : ResponseEntity<Unit> = ResponseEntity.status(HttpStatus.NO_CONTENT).body(courseService.deleteCourse(courseId)) // No-Content : body 없음

}