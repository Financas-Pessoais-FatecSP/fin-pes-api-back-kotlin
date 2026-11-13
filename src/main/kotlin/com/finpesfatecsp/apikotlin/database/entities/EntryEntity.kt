package com.finpesfatecsp.apikotlin.database.entities

import com.finpesfatecsp.apikotlin.database.entities.enums.EntryTypeEnum
import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.JoinColumn
import jakarta.persistence.ManyToOne
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
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_conta", referencedColumnName = "id", nullable = false)
    val account: AccountEntity,
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_categoria", referencedColumnName = "id", nullable = true)
    var category: EntryCategoryEntity? = null,
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_lancamento", unique = false, nullable = false)
    var type: EntryTypeEnum,
    @Column(name = "tx_descricao", unique = false, nullable = false)
    var description: String,
    @Column(name = "vl_lancamento", unique = false, nullable = false)
    var amount: Double,
    @Column(name = "dh_lancamento", unique = false, nullable = false)
    var date: LocalDate,
    @Column(name = "tx_observacao", unique = false, nullable = true)
    var details: String?,
)
