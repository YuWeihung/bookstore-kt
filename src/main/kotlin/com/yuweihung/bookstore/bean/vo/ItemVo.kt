package com.yuweihung.bookstore.bean.vo

import java.math.BigDecimal

/**
 * 向前端返回的商品项信息
 */
class ItemVo(
    val id: Long?,
    val name: String,
    val price: BigDecimal,
    val amount: Int,
)
