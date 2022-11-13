package com.yuweihung.bookstore.bean.entity

import jakarta.persistence.*
import java.math.BigDecimal

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

    @Column(name = "total_price", nullable = false)
    var totalPrice: BigDecimal,

    @Column(name = "phone_number", nullable = false)
    var phoneNumber: String,

    @Column(name = "address", nullable = false)
    var address: String,

    ) : BaseEntity() {

    @Column(name = "status", nullable = false)
    var status: OrderStatus = OrderStatus.NOT_PAID
}
