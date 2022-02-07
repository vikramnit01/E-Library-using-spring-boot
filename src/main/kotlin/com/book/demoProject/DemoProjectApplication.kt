package com.book.demoProject

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
class DemoProjectApplication

fun main(args: Array<String>) {
	runApplication<DemoProjectApplication>(*args)
}
