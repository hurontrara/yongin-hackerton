package com.hackerton.server.practice.controller

import com.hackerton.server.practice.dto.InstructorDTO
import com.hackerton.server.practice.service.InstructorService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient


@WebMvcTest(InstructorController::class) // 해당 컨트롤러만 빈 등록, 서비스, 리포지토리 계층은 MockBean
@AutoConfigureWebTestClient
class InstructorControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var instructorService: InstructorService

    @Test
    fun addInstructor() {

        //given
        val request = InstructorDTO(null, "name2")

        every { instructorService.addInstructor(any()) } returns InstructorDTO(1, "name2")

        //when
        val response = webTestClient
                .post()
                .uri("/v1/instructor")
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated
                .expectBody(InstructorDTO::class.java)
                .returnResult()
                .responseBody


        //then
        Assertions.assertThat(response!!.id).isNotNull

    }

    @Test
    fun addInstructorValidation() {

        //given
        val request = InstructorDTO(null, "")

        every { instructorService.addInstructor(any()) } returns InstructorDTO(1, "name2")

        //when
        webTestClient
                .post()
                .uri("/v1/instructor")
                .bodyValue(request)
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
                .jsonPath("$[0]").isEqualTo("InstructorDTO.name must be blank")
                .returnResult()
                .responseBody

    }






}