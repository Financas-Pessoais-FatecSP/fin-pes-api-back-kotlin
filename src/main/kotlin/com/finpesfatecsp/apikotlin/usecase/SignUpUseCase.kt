package com.finpesfatecsp.apikotlin.usecase

import com.finpesfatecsp.apikotlin.database.entities.UserEntity
import com.finpesfatecsp.apikotlin.database.repository.UserRepository
import com.finpesfatecsp.apikotlin.exceptions.BusinessException
import org.springframework.stereotype.Component

@Component
class SignUpUseCase(
    private val userRepository: UserRepository,
) {
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
                password = dto.password,
                picture = dto.picture,
            )

        return userRepository.save(userEntity).let {
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
