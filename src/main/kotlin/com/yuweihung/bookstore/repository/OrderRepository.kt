package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Order
import org.springframework.data.jpa.repository.JpaRepository

interface OrderRepository : JpaRepository<Order, Long> {
    fun findByUser_Id(id: Long): List<Order>

}
