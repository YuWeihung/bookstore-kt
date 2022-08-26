package com.yuweihung.bookstore.bean.entity

import javax.persistence.Entity
import javax.persistence.ManyToMany
import javax.persistence.Table

/**
 * 作者实体类
 */
@Entity
@Table(name = "author")
class Author(
    var name: String,
) : BaseEntity() {

    @ManyToMany(mappedBy = "authors")
    var books: MutableSet<Book> = mutableSetOf()
}
