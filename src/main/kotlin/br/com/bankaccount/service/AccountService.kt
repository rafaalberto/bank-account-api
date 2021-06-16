package br.com.bankaccount.service

import br.com.bankaccount.persistence.entity.Account
import br.com.bankaccount.resource.AccountRequest

interface AccountService {
    fun findAll(): MutableList<Account>
    fun findById(id: Long) : Account
    fun save(request: AccountRequest) : Account
    fun delete(id: Long)
}