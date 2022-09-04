package com.yuweihung.bookstore.util

import org.springframework.data.domain.PageRequest

/**
 * 分页工具类
 */
class PageUtil {
    companion object {
        private const val PAGE_SIZE = 10

        fun pageRequest(page: Int): PageRequest {
            return PageRequest.of(page - 1, PAGE_SIZE)
        }
    }
}
