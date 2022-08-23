package com.yuweihung.bookstore.bean.entity

import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * 作者实体类
 */
@Entity
@Table(name = "author")
class Author(
    var name: String,

    @OneToMany(mappedBy = "author")
    var books: MutableSet<Book> = mutableSetOf(),
) : BaseEntity()
