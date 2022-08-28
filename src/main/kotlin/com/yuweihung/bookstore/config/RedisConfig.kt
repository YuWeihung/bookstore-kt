package com.yuweihung.bookstore.config

import com.yuweihung.bookstore.config.redis.CustomKeyGenerator
import org.springframework.cache.annotation.CachingConfigurerSupport
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.KeyGenerator
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

/**
 * Redis 配置类
 */
@EnableCaching
@Configuration
class RedisConfig(
    val keyGenerator: CustomKeyGenerator,
) : CachingConfigurerSupport() {
    /**
     *  配置 redisTemplate，设置 JSON 序列化器
     */
    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template: RedisTemplate<String, Any> = RedisTemplate()
        template.setConnectionFactory(factory)
        template.setEnableTransactionSupport(true)
        val redisSerializer = GenericJackson2JsonRedisSerializer()
        val stringSerializer = StringRedisSerializer()
        template.keySerializer = stringSerializer
        template.valueSerializer = redisSerializer
        template.hashKeySerializer = stringSerializer
        template.hashValueSerializer = redisSerializer
        template.afterPropertiesSet()
        return template
    }

    /**
     * redis cache 配置，设置序列化及存储时间等
     */
    @Bean
    fun cacheConfiguration(template: RedisTemplate<String, Any>): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(template.stringSerializer))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.valueSerializer))
            .disableCachingNullValues()
            .entryTtl(Duration.ofMinutes(5))
    }

    override fun keyGenerator(): KeyGenerator? {
        return keyGenerator
    }

}
