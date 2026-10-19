package com.finpesfatecsp.apikotlin.database.entities

import com.finpesfatecsp.apikotlin.database.entities.enums.EntryTypeEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table
import java.time.LocalDate

@Entity
@Table(name = "tb_lancamento")
class EntryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_lancamento")
    @SequenceGenerator(name = "tb_lancamento", sequenceName = "tb_lancamento_id_seq", allocationSize = 1)
    val id: Long?,
    @Column(name = "id_conta", unique = false, nullable = false)
    val accountId: Long,
    @Column(name = "id_categoria", unique = false, nullable = true)
    val categoryId: Long?,
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_lancamento", unique = false, nullable = false)
    val type: EntryTypeEnum,
    @Column(name = "tx_descricao", unique = false, nullable = false)
    val description: String,
    @Column(name = "vl_lancamento", unique = false, nullable = false)
    val amount: Double,
    @Column(name = "dh_lancamento", unique = false, nullable = false)
    val date: LocalDate,
    @Column(name = "tx_observacao", unique = false, nullable = true)
    val details: String?,
)
