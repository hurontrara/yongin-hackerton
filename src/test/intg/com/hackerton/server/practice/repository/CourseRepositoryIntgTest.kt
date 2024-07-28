package com.hackerton.server.practice.repository

import com.hackerton.server.practice.util.courseEntityList
import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.MethodSource
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.test.context.ActiveProfiles
import java.util.stream.Stream

@DataJpaTest // JPA 관련 설정 구현해줌(영속성 컨텍스트...)
@ActiveProfiles("test")
class CourseRepositoryIntgTest {

    @Autowired
    lateinit var courseRepository: CourseRepository

    @BeforeEach
    fun setUp() {

        courseRepository.deleteAll()
        courseRepository.saveAll(courseEntityList())

    }


    @Test
    fun findByNameContaining() {

        //given
        val emptyCourses = courseRepository.findByNameContaining("name33")
        val courses = courseRepository.findByNameContaining("name")


        assertThat(emptyCourses.size).isEqualTo(0)
        assertThat(courses.size).isEqualTo(3)

    }

    @Test
    fun findByNameContainingUsingNativeQuery() {

        //given
        val emptyCourses = courseRepository.findByNameUsingNativeQuery("name33")
        val courses = courseRepository.findByNameUsingNativeQuery("name")


        assertThat(emptyCourses.size).isEqualTo(0)
        assertThat(courses.size).isEqualTo(3)

    }

    @ParameterizedTest
    @MethodSource("courseAndSize") // 소스 메서드가 정적임을 요구
    fun findCoursesByNameByParameterizedSearch(name: String, expectedSize: Int) {

        val courses = courseRepository.findByNameUsingNativeQuery(name)

        assertThat(courses.size).isEqualTo(expectedSize)

    }


    companion object {

        @JvmStatic // 정적으로 매핑
        fun courseAndSize(): Stream<Arguments> {

            return Stream.of(Arguments.arguments("name", 3),
                    Arguments.arguments("name1", 1))

        }

    }






}