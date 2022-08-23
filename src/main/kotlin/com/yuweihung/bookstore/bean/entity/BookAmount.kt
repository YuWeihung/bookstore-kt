package com.yuweihung.bookstore.bean.entity

import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table

@Entity
@Table(name = "book_amount")
class BookAmount(
    @OneToOne
    var book: Book,

    var amount: Int,
) : BaseEntity()
