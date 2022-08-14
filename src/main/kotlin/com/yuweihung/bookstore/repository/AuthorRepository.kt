package com.yuweihung.bookstore.repository;

import com.yuweihung.bookstore.bean.entity.Author
import org.springframework.data.jpa.repository.JpaRepository

/**
 * 作者的数据库访问接口
 * @author Yu Weihong
 * @since 2022/7/15
 */
interface AuthorRepository : JpaRepository<Author, Long> {
}
