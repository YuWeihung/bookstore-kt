package com.yuweihung.bookstore.bean.entity

import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import javax.persistence.*

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
    var createdTime: LocalDateTime? = null

    @LastModifiedDate
    var modifiedTime: LocalDateTime? = null
}
