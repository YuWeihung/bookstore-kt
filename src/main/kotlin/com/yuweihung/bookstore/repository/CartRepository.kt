package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Cart
import org.springframework.data.jpa.repository.JpaRepository

interface CartRepository : JpaRepository<Cart, Long> {
}
