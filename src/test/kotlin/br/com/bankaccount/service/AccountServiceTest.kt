package br.com.bankaccount.service

import br.com.bankaccount.exception.AppException
import br.com.bankaccount.persistence.entity.Account
import br.com.bankaccount.persistence.repository.AccountRepository
import br.com.bankaccount.resource.AccountRequest
import br.com.bankaccount.service.impl.AccountServiceImpl
import io.mockk.every
import io.mockk.impl.annotations.InjectMockKs
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.http.HttpStatus
import java.math.BigDecimal
import java.util.Optional.ofNullable

@ExtendWith(MockKExtension::class)
class AccountServiceTest {

    @InjectMockKs
    lateinit var accountService: AccountServiceImpl

    @MockK
    lateinit var accountRepository: AccountRepository

    @Test
    fun `should find all accounts`() {
        every { accountRepository.findAll() } returns getAccounts()
        val result = accountService.findAll()
        verify(atLeast = 1) { accountRepository.findAll() }
        assertEquals(3, result.size)
    }

    @Test
    fun `should find account by id`() {
        every { accountRepository.findById(any()) } returns ofNullable(getCreatedAccount())
        val result = accountService.findById(1)
        verify(atLeast = 1) { accountRepository.findById(any()) }
        assertEquals(1, result.id)
    }

    @Test
    fun `should throws AppException when find analysis does not exists`() {
        val exception = Assertions.assertThrows(AppException::class.java) {
            every { accountRepository.findById(any()) } throws AppException("account not found", HttpStatus.NOT_FOUND)
            accountService.findById(1)
            verify(atLeast = 1) { accountRepository.findById(any()) }
        }
        assertEquals("account not found", exception.description)
    }

    @Test
    fun `should create account successfully`() {
        every { accountRepository.save(any<Account>()) } returns getCreatedAccount()

        val result = accountService.save(AccountRequest(null, "Rafael"))

        verify(atLeast = 1) { accountRepository.save(any<Account>()) }

        assertEquals(1, result.id)
        assertEquals("Rafael", result.name)
        assertEquals(BigDecimal.ZERO, result.balance)
    }

    @Test
    fun `should update account successfully`() {
        every { accountRepository.findById(any()) } returns ofNullable(getCreatedAccount())
        every { accountRepository.save(any<Account>()) } returns getUpdatedAccount()

        val result = accountService.save(AccountRequest(1, "John"))

        verify(atLeast = 1) { accountRepository.findById(any()) }
        verify(atLeast = 1) { accountRepository.save(any<Account>()) }

        assertEquals(1, result.id)
        assertEquals("John", result.name)
        assertEquals(BigDecimal.ZERO, result.balance)
    }

    @Test
    fun `should delete account successfully`() {
        every { accountRepository.findById(any()) } returns ofNullable(getCreatedAccount())
        every { accountRepository.delete(any()) } returns Unit

        accountService.delete(1)

        verify(atLeast = 1) { accountRepository.findById(any()) }
        verify(atLeast = 1) { accountRepository.delete(any()) }
    }

    private fun getCreatedAccount() = Account(1, "Rafael", BigDecimal.ZERO)

    private fun getUpdatedAccount() = Account(1, "John", BigDecimal.ZERO)

    private fun getAccounts(): MutableList<Account> {
        return mutableListOf(
            Account(1, "John", BigDecimal.ZERO),
            Account(2, "Mary", BigDecimal.ZERO),
            Account(3, "Peter", BigDecimal.ZERO)
        )
    }

}