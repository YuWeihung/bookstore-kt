package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Item
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 商品项的数据库访问接口
 */
interface ItemRepository : JpaRepository<Item, Long> {
}
