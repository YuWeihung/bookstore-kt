package com.yuweihung.bookstore.bean.entity

import java.math.BigDecimal
import java.time.YearMonth
import javax.persistence.Entity
import javax.persistence.ManyToOne
import javax.persistence.Table

/**
 * 书籍实体类
 */
@Entity
@Table(name = "book")
class Book(
    var name: String,

    var isbn: String,

    var price: BigDecimal,

    var pageCount: Int,

    var publishDate: YearMonth,

    var inventory: Int,

    @ManyToOne
    var press: Press,

    @ManyToOne
    var author: Author,
) : BaseEntity()
