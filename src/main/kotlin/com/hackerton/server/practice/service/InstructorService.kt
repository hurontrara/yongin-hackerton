package com.hackerton.server.practice.service

import com.hackerton.server.practice.dto.InstructorDTO
import com.hackerton.server.practice.entity.Instructor
import com.hackerton.server.practice.repository.InstructorRepository
import org.springframework.stereotype.Service

@Service
class InstructorService(val instructorRepository: InstructorRepository) {

    fun addInstructor(instructorDTO: InstructorDTO) : InstructorDTO {

        val entity = instructorDTO.let {

            Instructor(it.id, it.name)

        }

        instructorRepository.save(entity)

        return entity.let {
            InstructorDTO(it.id, it.name)
        }


    }



}