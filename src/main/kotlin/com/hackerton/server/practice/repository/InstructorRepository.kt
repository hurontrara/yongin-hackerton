package com.hackerton.server.practice.repository

import com.hackerton.server.practice.entity.Instructor
import org.springframework.data.repository.CrudRepository

interface InstructorRepository : CrudRepository<Instructor, Int> {

}
