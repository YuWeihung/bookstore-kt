package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Address
import org.springframework.data.jpa.repository.JpaRepository

interface AddressRepository : JpaRepository<Address, Long> {
}
