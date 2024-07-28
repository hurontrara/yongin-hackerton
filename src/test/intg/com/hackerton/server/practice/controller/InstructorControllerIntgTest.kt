package com.hackerton.server.practice.controller

import com.hackerton.server.practice.dto.InstructorDTO
import com.hackerton.server.practice.repository.InstructorRepository
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient // testImpl -> webflux
class InstructorControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    @BeforeEach
    fun setUp() {
        instructorRepository.deleteAll()
    }

    @Test
    fun addInstructor() {

        //given
        val request = InstructorDTO(null, "Instructor2")


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


}