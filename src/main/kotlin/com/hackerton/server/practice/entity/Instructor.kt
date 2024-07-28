package com.hackerton.server.practice.entity

import jakarta.persistence.*


@Entity
@Table(name = "INSTRUCTOR")
data class Instructor (

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id : Int?,
        var name : String,

        @OneToMany(
                mappedBy = "instructor",
                cascade = [CascadeType.ALL],
                orphanRemoval = true
        )
        var courses : List<Course>? = null


)
