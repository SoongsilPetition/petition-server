package com.petition.petition

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class PetitionApplication

fun main(args: Array<String>) {
	runApplication<PetitionApplication>(*args)
}
