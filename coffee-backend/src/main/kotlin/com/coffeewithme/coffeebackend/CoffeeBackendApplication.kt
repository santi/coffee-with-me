package com.coffeewithme.coffeebackend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoffeeBackendApplication

fun main(args: Array<String>) {
	runApplication<CoffeeBackendApplication>(*args)
}
