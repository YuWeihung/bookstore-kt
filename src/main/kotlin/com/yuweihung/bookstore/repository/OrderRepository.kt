package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Order
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 订单的数据库访问接口
 */
interface OrderRepository : JpaRepository<Order, Long> {
    // 根据用户名查询订单
    fun findByUser_UsernameOrderByIdAsc(username: String, pageable: Pageable): Page<Order>

}
