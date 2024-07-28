package com.hackerton.server.practice.dto

import jakarta.validation.constraints.NotBlank

data class InstructorDTO (

    val id: Int?,

    @get:NotBlank(message = "InstructorDTO.name must be blank")
    val name: String


)