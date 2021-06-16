package br.com.bankaccount.service

import br.com.bankaccount.persistence.entity.Account
import br.com.bankaccount.persistence.repository.AccountRepository
import br.com.bankaccount.service.impl.AccountServiceImpl
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.math.BigDecimal
import java.util.Optional.ofNullable

@ExtendWith(MockKExtension::class)
class AccountServiceTest {

    @InjectMockKs
    lateinit var accountService: AccountServiceImpl

    @MockK
    lateinit var accountRepository: AccountRepository

    @Test
    fun findById() {
        every { accountRepository.findById(any()) } returns ofNullable(getAccount())
        val result = accountService.findById(1L)
        verify(atLeast = 1) { accountRepository.findById(any()) }
        assertEquals(1L, result.id)
    }

    private fun getAccount() = Account(1L, "Rafael", BigDecimal.ZERO)
}