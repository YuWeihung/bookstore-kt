package com.yuweihung.bookstore.bean.entity

import java.math.BigDecimal
import java.time.YearMonth
import javax.persistence.*

/**
 * 书籍实体类
 */
@Entity
@Table(
    name = "book",
    indexes = [
        Index(name = "idx_name", columnList = "name"),
        Index(name = "uni_isbn", columnList = "isbn", unique = true)
    ]
)
class Book(
    var name: String,

    var isbn: String,

    @Column(precision = 12, scale = 4)
    var price: BigDecimal,

    var pageCount: Int,

    var publishDate: YearMonth,

    var inventory: Int,

    @ManyToOne
    var press: Press,

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "book_author",
        joinColumns = [JoinColumn(name = "book_id")],
        inverseJoinColumns = [JoinColumn(name = "author_id")]
    )
    var authors: MutableSet<Author> = mutableSetOf(),
) : BaseEntity()
