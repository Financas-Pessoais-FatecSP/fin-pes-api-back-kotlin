package com.finpesfatecsp.apikotlin.database.entities

import com.finpesfatecsp.apikotlin.database.entities.enums.AccountStatusEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "tb_conta")
class AccountEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_conta")
    @SequenceGenerator(name = "tb_conta", sequenceName = "tb_conta_id_seq", allocationSize = 1)
    val id: Long?,
    @Column(name = "id_usuario", unique = false, nullable = false)
    val userId: Long,
    @Column(name = "tx_nome", unique = false, nullable = false)
    val name: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "tx_status", unique = false, nullable = false)
    val status: AccountStatusEnum = AccountStatusEnum.ACTIVE,
)
