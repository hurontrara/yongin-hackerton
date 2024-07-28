package com.hackerton.server.practice.controller

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient // testImpl -> webflux
class GreetingControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Test
    fun retrieveGreeting() {
        //given
        val name = "DoIt"


        //when
        val result = webTestClient.get()
                .uri("/v1/greetings/{name}", name)
                .exchange()
                .expectStatus().is2xxSuccessful
                .expectBody(String::class.java)
//                .isEqualTo("Hello DoIt")
                .returnResult()


        //then
        assertThat(result.responseBody).isEqualTo("Hello DoIt")


    }






}