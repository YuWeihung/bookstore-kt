package com.yuweihung.bookstore.bean.entity

import java.math.BigDecimal
import javax.persistence.*

/**
 * 订单实体类
 */
@Entity
@Table(name = "\"order\"")
class Order(
    @ManyToOne
    var user: User,

    @OneToMany
    @JoinTable(
        name = "order_item",
        joinColumns = [JoinColumn(name = "order_id")],
        inverseJoinColumns = [JoinColumn(name = "item_id")]
    )
    var items: MutableSet<Item> = mutableSetOf(),

    var totalPrice: BigDecimal,
) : BaseEntity()
