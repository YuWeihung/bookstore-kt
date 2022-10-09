package com.yuweihung.bookstore.bean.entity

import javax.persistence.*

/**
 * 出版社实体类
 */
@Entity
@Table(
    name = "press",
    indexes = [
        Index(name = "uni_name", columnList = "name", unique = true),
    ]
)
class Press(
    @Column(name = "name", nullable = false)
    var name: String,
) : BaseEntity() {

    @OneToMany(mappedBy = "press")
    var books: MutableSet<Book> = mutableSetOf()
}
