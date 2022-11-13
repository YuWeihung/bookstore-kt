package com.yuweihung.bookstore.bean.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.OneToOne
import jakarta.persistence.Table

/**
 * 商品项实体类
 */
@Entity
@Table(name = "item")
class Item(
    @OneToOne
    var book: Book,

    @Column(name = "amount", nullable = false)
    var amount: Int,
) : BaseEntity()
