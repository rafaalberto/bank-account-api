package br.com.bankaccount.resource

import br.com.bankaccount.service.AccountService
import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.CREATED
import org.springframework.http.HttpStatus.OK
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/accounts")
class AccountResource(private val accountService: AccountService) {

    @GetMapping
    fun findAll() = ResponseEntity.ok(accountService.findAll())

    @GetMapping("/{id}")
    fun findById(@PathVariable id: Long) = ResponseEntity.ok(accountService.findById(id))

    @PostMapping
    fun create(@RequestBody request: AccountRequest) = ResponseEntity(accountService.save(request), CREATED)

    @PutMapping("/{id}")
    fun update(@PathVariable id: Long, @RequestBody request: AccountRequest) =
            ResponseEntity(accountService.save(request.copy(id = id)), OK)

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    fun delete(@PathVariable id: Long) = accountService.delete(id)
}

data class AccountRequest(val id: Long? = null, val name: String)