package com.hackerton.server.practice.util

import com.hackerton.server.practice.entity.Course
import com.hackerton.server.practice.entity.Instructor

fun courseEntityList(instructor: Instructor? = null) = listOf(
        Course(null, "name1", "category1", instructor),
        Course(null, "name2", "category2", instructor),
        Course(null, "name3", "category3", instructor))