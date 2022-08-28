package com.yuweihung.bookstore.bean.dto

import javax.validation.constraints.Min

/**
 * 修改库存接收的参数
 */
data class InventoryDto(
    val bookId: Long,
    @get: Min(0, message = "库存不能为负")
    val inventory: Int,
)
