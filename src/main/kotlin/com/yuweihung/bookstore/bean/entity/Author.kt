package com.yuweihung.bookstore.bean.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * 作者实体类
 * @author Yu Weihong
 * @since 2022/7/15
 */
@Entity
@Table(name = "author")
class Author(
    var name: String,

    @JsonIgnore
    @OneToMany(mappedBy = "author")
    var books: MutableSet<Book> = mutableSetOf(),
) : BaseEntity()
