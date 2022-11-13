package com.yuweihung.bookstore.bean.entity

import jakarta.persistence.*
import java.math.BigDecimal
import java.time.YearMonth

/**
 * 书籍实体类
 */
@Entity
@Table(
    name = "book",
    indexes = [
        Index(name = "idx_name", columnList = "name", unique = false),
        Index(name = "uni_isbn", columnList = "isbn", unique = true),
    ]
)
class Book(
    @Column(name = "name", nullable = false)
    var name: String,

    @Column(name = "isbn", nullable = false)
    var isbn: String,

    @Column(name = "price", nullable = false)
    var price: BigDecimal,

    @Column(name = "page", nullable = false)
    var page: Int,

    @Column(name = "publish_date", nullable = false)
    var publishDate: YearMonth,

    @Column(name = "inventory", nullable = false)
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
