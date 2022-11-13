package com.yuweihung.bookstore.bean.entity

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

/**
 * 基础实体类，其他实体由此继承
 * 开启审计，自动更新实体的创建和修改时间
 */
@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    var id: Long? = null

    @CreatedDate
    @Column(name = "created_time", nullable = false)
    var createdTime: LocalDateTime? = null

    @LastModifiedDate
    @Column(name = "modified_time", nullable = false)
    var modifiedTime: LocalDateTime? = null
}
