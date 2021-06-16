package br.com.bankaccount.service

import br.com.bankaccount.persistence.entity.Account
import br.com.bankaccount.persistence.repository.AccountRepository
import br.com.bankaccount.service.impl.AccountServiceImpl
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class AccountServiceTest {

    @InjectMockKs
    lateinit var accountService: AccountServiceImpl

    @MockK
    lateinit var accountRepository: AccountRepository

    @BeforeEach
    fun setUp() {
        MockKAnnotations.init(this)
        accountService = AccountServiceImpl(accountRepository)
    }

    @Test
    fun findById() {
        var account = Account (1L, "Rafael", BigDecimal.ZERO)
        every { accountRepository.findById(any()) } returns java.util.Optional.ofNullable(account)

        val result = accountService.findById(1L)

        verify(atLeast = 1) { accountRepository.findById(any()) }

        Assertions.assertEquals(1L, result.id)

    }

}