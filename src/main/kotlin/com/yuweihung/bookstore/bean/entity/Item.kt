package com.yuweihung.bookstore.bean.entity

import javax.persistence.Entity
import javax.persistence.OneToOne
import javax.persistence.Table

/**
 * 商品项实体类
 */
@Entity
@Table(name = "item")
class Item(
    @OneToOne
    var book: Book,

    var amount: Int,
) : BaseEntity()
