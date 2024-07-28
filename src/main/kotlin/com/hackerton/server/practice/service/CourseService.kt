package com.hackerton.server.practice.service

import com.hackerton.server.practice.dto.CourseDTO
import com.hackerton.server.practice.entity.Course
import com.hackerton.server.practice.exception.InstructorNotValidException
import com.hackerton.server.practice.repository.CourseRepository
import com.hackerton.server.practice.repository.InstructorRepository
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class CourseService(val courseRepository: CourseRepository,
                    val instructorRepository: InstructorRepository) {


    fun addCourse(courseDTO: CourseDTO) : CourseDTO {

        // 그냥 널처리 하고 가기
        val instructor = instructorRepository.findById(courseDTO.instructorId!!).orElseThrow { InstructorNotValidException("Instructor not found with ID") }

        val courseEntity = courseDTO.let {
            Course(null, it.name, it.category, instructor)
        }

        courseRepository.save(courseEntity)

        return courseEntity.let {
            CourseDTO(it.id, it.name, it.category, instructor.id)
        }
        
    }

    fun retrieveAllCourses(courseName: String?): List<CourseDTO> {

        val result = courseName?.let {

            courseRepository.findByNameContaining(courseName)

        } ?: courseRepository.findAll()

        return result.map { CourseDTO(it.id, it.name, it.category) }

    }





    fun updateCourse(courseId: Int, courseDTO: CourseDTO) : CourseDTO {

        val course = courseRepository.findByIdOrNull(courseId)

        return course?.let {
            it.name = courseDTO.name
            it.category = courseDTO.category
            CourseDTO(it.id, it.name, it.category)
        } ?: throw RuntimeException("NOT EXIST ID FOR PUT")


    }

    fun deleteCourse(courseId: Int) {

        // findById와 조합해서 사용 -> deleteById는 void이므로, 에러 커스텀이 힘듬
        val course = courseRepository.findByIdOrNull(courseId)

        return course?.let {

            courseRepository.deleteById(courseId)

        } ?: throw RuntimeException("NOT EXIST ID FOR DELETE")


    }


}