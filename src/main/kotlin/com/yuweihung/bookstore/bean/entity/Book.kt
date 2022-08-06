package com.yuweihung.bookstore.bean.entity

import javax.persistence.Entity
import javax.persistence.Table

/**
 * 书籍实体
 * @author Yu Weihong
 * @since 2022/7/15
 */
@Entity
@Table(name = "book")
class Book(
    var name: String = "",
) : BaseEntity()