package com.hackerton.server.practice.dto

import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class CourseDTO (

    val id : Int?,

    @get:NotBlank(message = "CourseDTO.name must be blank")
    val name: String,

    @get:NotBlank(message = "CourseDTO.category must be blank")
    val category: String,

    @get:NotNull(message = "CourseDTO.instructorId must be blank")  // 숫자에는 notblank 불ㄱ
    val instructorId : Int? = null

)