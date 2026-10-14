package com.finpesfatecsp.apikotlin.business.usecase

import com.finpesfatecsp.apikotlin.business.exceptions.BusinessException
import com.finpesfatecsp.apikotlin.business.usecase.dto.SignUpDTO
import com.finpesfatecsp.apikotlin.database.entities.AccountEntity
import com.finpesfatecsp.apikotlin.database.entities.UserEntity
import com.finpesfatecsp.apikotlin.database.repository.spec.AccountRepository
import com.finpesfatecsp.apikotlin.database.repository.spec.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion
import org.springframework.stereotype.Component

@Component
class SignUpUseCase(
    private val userRepository: UserRepository,
    private val accountRepository: AccountRepository,
) {
    private val passwordEncoder = BCryptPasswordEncoder(BCryptVersion.`$2B`)
    private val firstAccountName = "Gastos Fixos Mensais"

    fun signUp(dto: SignUpDTO): SignUpDTO {
        if (userRepository.findByEmail(dto.email) != null) {
            throw BusinessException(
                message = "User with email ${dto.email} already exists",
                code = "user.already.exists",
            )
        }

        val userEntity =
            UserEntity(
                id = null,
                name = dto.name,
                surname = dto.surname,
                email = dto.email,
                password = passwordEncoder.encode(dto.password) ?: throw Exception("Password encoding failed"),
                picture = dto.picture,
            )

        val savedUser = userRepository.save(userEntity)

        accountRepository.save(
            AccountEntity(
                id = null,
                userId = savedUser.id ?: throw Exception("User ID is null"),
                name = firstAccountName,
            ),
        )

        return savedUser.let {
            SignUpDTO(
                id = it.id,
                name = it.name,
                surname = it.surname,
                email = it.email,
                password = it.password,
                picture = it.picture,
            )
        }
    }
}
