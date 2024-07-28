package com.hackerton.server.practice.repository

import com.hackerton.server.practice.entity.Course
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository

interface CourseRepository : CrudRepository<Course, Int> {


    fun findByNameContaining(courseName : String) : List<Course>

    @Query(value = "SELECT c FROM Course c WHERE c.name like %?1%")
    fun findByNameUsingNativeQuery(courseName: String) : List<Course>

}