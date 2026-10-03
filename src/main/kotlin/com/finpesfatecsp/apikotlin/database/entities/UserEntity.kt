package com.finpesfatecsp.apikotlin.database.entities

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.SequenceGenerator
import jakarta.persistence.Table

@Entity
@Table(name = "tb_usuario")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tb_usuario")
    @SequenceGenerator(name = "tb_usuario", sequenceName = "tb_usuario_id_seq", allocationSize = 1)
    val id: Long?,
    @Column(name = "tx_nome", unique = false, nullable = false)
    val name: String,
    @Column(name = "tx_sobrenome", unique = false, nullable = false)
    val surname: String,
    @Column(name = "tx_email", unique = true, nullable = false)
    val email: String,
    @Column(name = "tx_senha", unique = false, nullable = false)
    val password: String,
    @Column(name = "tx_foto", unique = false, nullable = true)
    val picture: String?,
)
