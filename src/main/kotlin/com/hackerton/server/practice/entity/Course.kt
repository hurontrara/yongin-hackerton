package com.hackerton.server.practice.entity

import jakarta.persistence.*


@Entity
@Table(name =  "COURSE")
data class Course(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,

        var name: String,
        var category: String,

        // toString 활용해서 ID 반환하거나 하도록
        @ManyToOne(fetch = FetchType.LAZY)
        @JoinColumn(name = "INSTRUCTOR_ID", nullable = false)
        val instructor: Instructor? = null



)