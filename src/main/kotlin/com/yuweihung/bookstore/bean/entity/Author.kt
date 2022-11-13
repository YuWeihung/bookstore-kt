package com.yuweihung.bookstore.bean.entity

import jakarta.persistence.*

/**
 * 作者实体类
 */
@Entity
@Table(
    name = "author",
    indexes = [
        Index(name = "uni_name", columnList = "name", unique = true),
    ]
)
class Author(
    @Column(name = "name", nullable = false)
    var name: String,
) : BaseEntity() {

    @ManyToMany(mappedBy = "authors")
    var books: MutableSet<Book> = mutableSetOf()
}
