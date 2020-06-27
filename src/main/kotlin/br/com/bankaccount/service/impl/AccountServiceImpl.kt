package br.com.bankaccount.service.impl

import br.com.bankaccount.exception.AppException
import br.com.bankaccount.persistence.entity.Account
import br.com.bankaccount.persistence.repository.AccountRepository
import br.com.bankaccount.resource.AccountRequest
import br.com.bankaccount.service.AccountService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AccountServiceImpl(@Autowired private val accountRepository: AccountRepository) : AccountService {

    override fun findAll(): MutableList<Account> = accountRepository.findAll()

    override fun findById(id: Long): Account =
            accountRepository.findById(id).orElseThrow { AppException("account not found", NOT_FOUND) }

    override fun save(request: AccountRequest): Account {
        val account = Account(request.id, request.name, BigDecimal.ZERO)
        if (account.id !== null) {
            account.balance = findById(account.id).balance
        }
        return accountRepository.save(account)
    }

    override fun delete(id: Long) = accountRepository.delete(findById(id))
}