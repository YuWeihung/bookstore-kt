package com.yuweihung.bookstore.bean.entity

import javax.persistence.Entity
import javax.persistence.OneToMany
import javax.persistence.Table

/**
 * 出版社实体类
 */
@Entity
@Table(name = "press")
class Press(
    var name: String,
) : BaseEntity() {

    @OneToMany(mappedBy = "press")
    var books: MutableSet<Book> = mutableSetOf()
}
