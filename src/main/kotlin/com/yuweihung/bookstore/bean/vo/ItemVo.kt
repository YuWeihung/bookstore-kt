package com.yuweihung.bookstore.bean.vo

import java.math.BigDecimal

/**
 * 商品项信息
 */
class ItemVo(
    val bookId: Long?,
    val name: String,
    val price: BigDecimal,
    val amount: Int,
)
