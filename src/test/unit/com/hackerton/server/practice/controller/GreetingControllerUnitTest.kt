package com.hackerton.server.practice.controller

import com.hackerton.server.practice.service.GreetingService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient
import org.assertj.core.api.Assertions.*


@WebMvcTest(GreetingController::class) // 해당 컨트롤러만 빈 등록, 서비스, 리포지토리 계층은 MockBean
@AutoConfigureWebTestClient
class GreetingControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var greetingService: GreetingService

    @Test
    fun retrieveGreeting() {
        //given
        val name = "Stone"
        every { greetingService.retrieveGreeting(any()) } returns "Hello $name"

        //when
        val result = webTestClient.get()
                .uri("/v1/greetings/{name}", name)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody(String::class.java)
//                .isEqualTo("")
                .returnResult()

        //then
        assertThat(result.responseBody).isEqualTo("Hello $name")

    }





}