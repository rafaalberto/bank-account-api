package br.com.bankaccount.resource

import br.com.bankaccount.persistence.entity.Account
import br.com.bankaccount.service.AccountService
import com.google.gson.Gson
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.hamcrest.Matchers.`is`
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.math.BigDecimal

@ExtendWith(SpringExtension::class)
@WebMvcTest(AccountResource::class)
class AccountResourceTest {

    @TestConfiguration
    class ResourceTestConfig() {
        @Bean
        fun service() = mockk<AccountService>()
    }

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var accountService: AccountService

    @Autowired
    private lateinit var gson: Gson

    @Test
    fun `should find all accounts`() {
        every { accountService.findAll() } returns getAccounts()
        mockMvc.perform(
            get("/accounts")
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andDo(print())
        verify(atLeast = 1) { accountService.findAll() }
    }

    @Test
    fun `should find account by id`() {
        every { accountService.findById(1L) } returns getAccount()
        mockMvc.perform(get("/accounts/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name", `is`("Rafael")))
            .andDo(print())
        verify(atLeast = 1) { accountService.findById(any()) }
    }

    @Test
    fun `should create account successfully`() {
        every { accountService.save(any()) } returns getAccountCreated()
        mockMvc.perform(post("/accounts")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(getRequestToCreate())))
            .andExpect(status().isCreated)
            .andExpect(jsonPath("$.name", `is`("Rafael")))
            .andDo(print())
        verify(atLeast = 1) { accountService.save(any()) }
    }

    @Test
    fun `should update account successfully`() {
        every { accountService.save(any()) } returns getAccountUpdated()
        mockMvc.perform(put("/accounts/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(gson.toJson(getRequestToUpdate())))
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name", `is`("John")))
            .andDo(print())
        verify(atLeast = 1) { accountService.save(any()) }
    }

    @Test
    fun `should delete account successfully`() {
        every { accountService.delete(1L) } returns Unit
        mockMvc.perform(delete("/accounts/1")
            .contentType(MediaType.APPLICATION_JSON))
            .andExpect(status().isNoContent)
            .andDo(print())
        verify(atLeast = 1) { accountService.delete(any()) }
    }

    private fun getAccount() = Account(1L, "Rafael", BigDecimal.ZERO)

    private fun getAccounts(): MutableList<Account> {
        return mutableListOf(
            Account(1, "John", BigDecimal.ZERO),
            Account(2, "Mary", BigDecimal.ZERO),
            Account(3, "Peter", BigDecimal.ZERO)
        )
    }

    private fun getAccountCreated(): Account {
        return Account(1L, "Rafael", BigDecimal.valueOf(5.00))
    }

    private fun getAccountUpdated(): Account {
        return Account(1L, "John", BigDecimal.valueOf(5.00))
    }

    private fun getRequestToCreate(): AccountRequest {
        return AccountRequest(null, "Rafael")
    }

    private fun getRequestToUpdate(): AccountRequest {
        return AccountRequest(1L, "John")
    }

}