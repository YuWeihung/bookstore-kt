package com.yuweihung.bookstore.config.redis

import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.stereotype.Component
import java.lang.reflect.Method

@Component
class CustomKeyGenerator : KeyGenerator {
    override fun generate(target: Any, method: Method, vararg params: Any?): Any {
        val methodName = method.name
        var key = "#$methodName"
        for (param in params) {
            key += "#$param"
        }
        return key
    }
}
