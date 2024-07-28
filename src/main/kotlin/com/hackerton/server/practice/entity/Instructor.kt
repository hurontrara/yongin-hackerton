package com.hackerton.server.practice.entity

import com.hackerton.server.global.entity.BaseEntity
import jakarta.persistence.*


@Entity
@Table(name = "INSTRUCTOR")
class Instructor (

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


) : BaseEntity()
