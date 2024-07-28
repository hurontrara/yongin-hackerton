package com.hackerton.server.practice.controller

import com.hackerton.server.practice.service.GreetingService
import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.beans.factory.annotation.Value
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/v1/greetings")
class GreetingController(val greetingService: GreetingService) {

    @Value("\${message}")
    lateinit var message: String

    private val logger = KotlinLogging.logger {}

    @GetMapping("/{name}")
    fun retrieveGreeting(@PathVariable("name") name: String) = greetingService.retrieveGreeting(name)

    @GetMapping("/log/{name}")
    fun retrieveGreetingWithLog(@PathVariable("name") name: String) : String {

        logger.info { "Name is $name" }
        logger.info { "$message" }

        return greetingService.retrieveGreeting(name)

    }

}