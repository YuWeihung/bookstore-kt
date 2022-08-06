package com.yuweihung.bookstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.data.jpa.repository.config.EnableJpaAuditing

@EnableJpaAuditing
@SpringBootApplication
class BookstoreApplication

fun main(args: Array<String>) {
    runApplication<BookstoreApplication>(*args)
}
