package com.hackerton.server.practice.controller

import com.hackerton.server.practice.dto.CourseDTO
import com.hackerton.server.practice.service.CourseService
import com.ninjasquad.springmockk.MockkBean
import io.mockk.every
import io.mockk.just
import io.mockk.runs
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.reactive.server.WebTestClient

@WebMvcTest(CourseController::class) // 해당 컨트롤러만 빈 등록, 서비스, 리포지토리 계층은 MockBean
@AutoConfigureWebTestClient
class CourseControllerUnitTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @MockkBean
    lateinit var courseService: CourseService

    @Test
    fun addCourse() {

        //given
        val courseDTO = CourseDTO(null, "name2", "category2", 1)

        every { courseService.addCourse(any()) } returns CourseDTO(1, "name2", "category2", 1)

        //when
        val savedCourseDTO = webTestClient
                .post()
                .uri("/v1/course")
                .bodyValue(courseDTO)
                .exchange()
                .expectStatus().isCreated
                .expectBody(CourseDTO::class.java)
                .returnResult()
                .responseBody


        //then
        assertThat(savedCourseDTO!!.id).isNotNull

    }

    @Test
    fun addCourseValidation() {

        //given
        val courseDTO = CourseDTO(null, "", "", 1)

        every { courseService.addCourse(any()) } returns CourseDTO(1, "name2", "category2", 1)

        //when
        webTestClient
                .post()
                .uri("/v1/course")
                .bodyValue(courseDTO)
                .exchange()
                .expectStatus().isBadRequest
                .expectBody()
                .jsonPath("$[0]").isEqualTo("CourseDTO.category must be blank")
                .jsonPath("$[1]").isEqualTo("CourseDTO.name must be blank")

        //then

    }




    @Test
    fun retrieveAll() {

        //given
        every { courseService.retrieveAllCourses(any()) }.returnsMany(
                listOf(CourseDTO(1, "name1", "category1"),
                        CourseDTO(2, "name2", "category2"),
                        CourseDTO(3, "name3", "category3")
            )
        )

        //when
        val courseDTOs = webTestClient.get()
                .uri("/v1/course")
                .exchange()
                .expectStatus().isOk
                .expectBodyList(CourseDTO::class.java)
                .returnResult()
                .responseBody


        //then
        assertThat(courseDTOs!!.size).isEqualTo(3)


    }

    @Test
    fun updateCourse() {
        //given

        val request = CourseDTO(null, "name2", "category2")
        every { courseService.updateCourse(any(), any()) } returns CourseDTO(100, "name2", "category2")

        //when
        val result = webTestClient.put()
                .uri("/v1/course/{courseId}", 100)
                .bodyValue(request)
                .exchange()
                .expectStatus().isCreated
                .expectBody(CourseDTO::class.java)
                .returnResult()
                .responseBody


        //then
        assertThat(result!!.id).isEqualTo(100)


    }

    @Test
    fun deleteCourse() {

        //given
        every { courseService.deleteCourse(any()) } just runs

        //when
        webTestClient.delete()
                .uri("/v1/course/{courseId}", 1000)
                .exchange()
                .expectStatus().isNoContent

    }






}