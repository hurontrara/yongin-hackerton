package com.hackerton.server.global.entity

import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.PreUpdate
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class BaseEntity {

    // 영속화 시점에 작동하는 코드 -> var로 열어둠

    @CreatedDate
    var createdDate: LocalDateTime? = null

    @LastModifiedDate
    var updatedDate: LocalDateTime? = null

    @PrePersist
    fun prePersist() {

        createdDate = LocalDateTime.now()
        updatedDate = LocalDateTime.now()

    }

    @PreUpdate
    fun preUpdate() {
        updatedDate = LocalDateTime.now()
    }

}