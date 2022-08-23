package com.yuweihung.bookstore.config

import com.fasterxml.jackson.annotation.JsonAutoDetect
import com.fasterxml.jackson.annotation.JsonTypeInfo
import com.fasterxml.jackson.annotation.PropertyAccessor
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.cache.RedisCacheConfiguration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.RedisSerializationContext
import org.springframework.data.redis.serializer.StringRedisSerializer
import java.time.Duration

/**
 * Redis 配置类
 */
@EnableCaching
@Configuration
class RedisConfig {

    @Bean
    fun redisTemplate(factory: RedisConnectionFactory): RedisTemplate<String, Any> {
        val template: RedisTemplate<String, Any> = RedisTemplate()
        template.setConnectionFactory(factory)
        template.setEnableTransactionSupport(true)
        val redisSerializer = Jackson2JsonRedisSerializer(Any::class.java)
        val mapper = jacksonObjectMapper()
        mapper.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY)
        mapper.activateDefaultTyping(
            mapper.polymorphicTypeValidator,
            ObjectMapper.DefaultTyping.NON_FINAL,
            JsonTypeInfo.As.PROPERTY
        )
        redisSerializer.setObjectMapper(mapper)
        val stringSerializer = StringRedisSerializer()
        template.keySerializer = stringSerializer
        template.valueSerializer = redisSerializer
        template.hashKeySerializer = stringSerializer
        template.hashValueSerializer = redisSerializer
        template.afterPropertiesSet()
        return template
    }

    @Bean
    fun cacheConfiguration(template: RedisTemplate<String, Any>): RedisCacheConfiguration {
        return RedisCacheConfiguration.defaultCacheConfig()
            .serializeKeysWith(RedisSerializationContext.SerializationPair.fromSerializer(template.stringSerializer))
            .serializeValuesWith(RedisSerializationContext.SerializationPair.fromSerializer(template.valueSerializer))
            .disableCachingNullValues()
            .entryTtl(Duration.ofMinutes(5))
    }

}
