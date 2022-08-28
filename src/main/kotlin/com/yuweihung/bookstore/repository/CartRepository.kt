package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 购物车的数据库访问接口
 */
interface CartRepository : JpaRepository<Cart, Long> {
}
