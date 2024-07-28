package com.hackerton.server.global.handler

import com.hackerton.server.practice.exception.InstructorNotValidException
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatusCode
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import java.lang.Exception


@RestControllerAdvice
class GlobalErrorHandler : ResponseEntityExceptionHandler() {

    private val logger = KotlinLogging.logger {}

    override fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException, headers: HttpHeaders, status: HttpStatusCode, request: WebRequest): ResponseEntity<Any>? {

        logger.error("MethodArgumentNotValidException observed")

        val errors = ex.bindingResult.allErrors
                .map { error -> error.defaultMessage!! }
                .sorted()

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors)

    }

    @ExceptionHandler(Exception::class) // 테스트 작성 생략 -> 서비스 mock 객체가 에러 발생시키도록
    fun handleAllException(ex: Exception, request: WebRequest): ResponseEntity<String> {

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ex.message)
    }

    @ExceptionHandler(InstructorNotValidException::class)
    fun handleInstructorNotValidException(ex: InstructorNotValidException, request: WebRequest): ResponseEntity<String> {

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ex.message)

    }

}