package com.yuweihung.bookstore.bean.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "cart")
class Cart : BaseEntity() {
    @OneToOne(mappedBy = "cart")
    var user: User? = null

    @OneToMany
    @JoinTable(
        name = "cart_book_item",
        joinColumns = [JoinColumn(name = "cart_id")],
        inverseJoinColumns = [JoinColumn(name = "book_item_id")]
    )
    var books: MutableSet<BookItem> = mutableSetOf()

    @Column(precision = 12, scale = 4)
    var totalPrice: BigDecimal = BigDecimal("0.00")
}
