package br.com.bankaccount.persistence.entity

import java.math.BigDecimal
import javax.persistence.*

@Entity(name = Account.TABLE_NAME)
data class Account(
        @Id
        @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = SEQUENCE_NAME)
        @SequenceGenerator(name = SEQUENCE_NAME, sequenceName = SEQUENCE_NAME, allocationSize = 1)
        val id: Long? = null,
        val name: String,
        var balance: BigDecimal) {
    companion object {
        const val TABLE_NAME = "accounts"
        const val SEQUENCE_NAME = "seq_accounts"
    }
}