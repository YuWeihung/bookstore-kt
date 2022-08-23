package com.yuweihung.bookstore.bean.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "cart")
class Cart(
    @OneToOne(mappedBy = "cart")
    var user: User,

    @OneToMany
    @JoinTable(
        name = "cart_book_amount",
        joinColumns = [JoinColumn(name = "cart_id")],
        inverseJoinColumns = [JoinColumn(name = "book_amount_id")]
    )
    var books: MutableSet<BookAmount> = mutableSetOf(),

    var totalPrice: BigDecimal,
) : BaseEntity()
