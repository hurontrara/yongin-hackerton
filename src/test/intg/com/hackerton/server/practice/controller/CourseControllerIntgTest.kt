package com.hackerton.server.practice.controller

import com.hackerton.server.practice.dto.CourseDTO
import com.hackerton.server.practice.entity.Course
import com.hackerton.server.practice.entity.Instructor
import com.hackerton.server.practice.repository.CourseRepository
import com.hackerton.server.practice.repository.InstructorRepository
import com.hackerton.server.practice.util.courseEntityList
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.data.repository.findByIdOrNull
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.web.util.UriComponentsBuilder


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@AutoConfigureWebTestClient // testImpl -> webflux
class CourseControllerIntgTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    @Autowired
    lateinit var courseRepository: CourseRepository

    @Autowired
    lateinit var instructorRepository: InstructorRepository

    val instructor = Instructor(null, "mrPark")

    @BeforeEach
    fun setUp() {

        courseRepository.deleteAll()
        instructorRepository.deleteAll()

        instructorRepository.save(instructor)

        courseRepository.saveAll(courseEntityList(instructor))

    }


    @Test
    fun addCourse() {

        //given
        val courseDTO = CourseDTO(null, "name2", "category2", 1)

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
    fun retrieveByName() {

        //given

        val uri = UriComponentsBuilder.fromUriString("/v1/course")
                .queryParam("course_name", "name1")
                .toUriString()

        //when
        val courseDTOs = webTestClient.get()
                .uri(uri)
                .exchange()
                .expectStatus().isOk
                .expectBodyList(CourseDTO::class.java)
                .returnResult()
                .responseBody


        //then
        assertThat(courseDTOs!!.size).isEqualTo(1)


    }


    @Test
    fun retrieveAll() {

        //given

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
        val course = Course(null, "name1", "category1", instructor)
        courseRepository.save(course)

        val courseDTO = CourseDTO(null, "name2", "category2")

        //when
        val result = webTestClient.put()
                .uri("/v1/course/{courseId}", course.id)
                .bodyValue(courseDTO)
                .exchange()
                .expectStatus().isCreated
                .expectBody(CourseDTO::class.java)
                .returnResult() // '그만 할래'의 의사 표현
                .responseBody


        //then
        assertThat(result!!).isEqualTo(CourseDTO(course.id, courseDTO.name, courseDTO.category))


    }

    @Test
    fun deleteCourse() {

        //given
        val course = Course(null, "name1", "category1", instructor)
        courseRepository.save(course)

        //when
        val result = webTestClient.delete()
                .uri("/v1/course/{courseId}", course.id)
                .exchange()
                .expectStatus().isNoContent
                .expectBody().isEmpty
                .responseBody

        //then
        assertThat(courseRepository.findByIdOrNull(course.id!!)).isNull()
        assertThat(result).isNull()

    }













}