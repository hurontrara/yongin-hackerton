package com.hackerton.server.practice.controller

import com.hackerton.server.practice.dto.InstructorDTO
import com.hackerton.server.practice.service.InstructorService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("v1/instructor")
@Validated
class InstructorController(val instructorService: InstructorService) {

    @PostMapping
    fun createInstructor(@Valid @RequestBody instructorDTO: InstructorDTO) : ResponseEntity<InstructorDTO> = ResponseEntity.status(HttpStatus.CREATED).body(instructorService.addInstructor(instructorDTO))

}