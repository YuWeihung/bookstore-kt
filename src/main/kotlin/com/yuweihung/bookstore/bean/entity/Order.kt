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
        name = "order_book_item",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "book_item_id")]
    )
    var books: MutableSet<BookItem> = mutableSetOf(),

    @Column(precision = 12, scale = 4)
    var totalPrice: BigDecimal,
) : BaseEntity()
