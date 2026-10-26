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

@Entity
@Table(name = "tb_categoria_lancamento")
class EntryCategoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_categoria_lancamento")
    @SequenceGenerator(name = "tb_categoria_lancamento", sequenceName = "tb_categoria_lancamento_id_seq", allocationSize = 1)
    val id: Long?,
    @Column(name = "id_usuario", unique = false, nullable = false, columnDefinition = "bigint references tb_usuario(id)")
    val userId: Long,
    @Column(name = "tx_nome", unique = false, nullable = false)
    val name: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "tp_categoria_lancamento", unique = false, nullable = false)
    val type: EntryTypeEnum,
)
