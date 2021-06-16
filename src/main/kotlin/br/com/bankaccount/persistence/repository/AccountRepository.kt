package br.com.bankaccount.persistence.repository

import br.com.bankaccount.persistence.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long>