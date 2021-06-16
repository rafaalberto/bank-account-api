package br.com.bankaccount

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BankAccountApplication

fun main(args: Array<String>) {
	runApplication<BankAccountApplication>(*args)
}
