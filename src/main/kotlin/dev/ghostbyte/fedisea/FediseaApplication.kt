package dev.ghostbyte.fedisea

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FediseaApplication

fun main(args: Array<String>) {
	runApplication<FediseaApplication>(*args)
}
