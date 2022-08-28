package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Item
import org.springframework.data.jpa.repository.JpaRepository

interface ItemRepository : JpaRepository<Item, Long> {
}
