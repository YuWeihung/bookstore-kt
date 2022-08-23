package com.yuweihung.bookstore.bean.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity
@Table(name = "\"order\"")
class Order(
    @ManyToOne
    var user: User,

    @OneToMany
    @JoinTable(
        name = "order_book_amount",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "book_amount_id")]
    )
    var books: MutableSet<BookAmount> = mutableSetOf(),

    var totalPrice: BigDecimal,
) : BaseEntity()
