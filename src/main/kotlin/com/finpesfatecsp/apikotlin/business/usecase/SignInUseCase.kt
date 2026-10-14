package com.finpesfatecsp.apikotlin.business.usecase

import com.finpesfatecsp.apikotlin.business.exceptions.BusinessException
import com.finpesfatecsp.apikotlin.business.usecase.dto.SignInDTO
import com.finpesfatecsp.apikotlin.database.repository.spec.UserRepository
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder.BCryptVersion
import org.springframework.stereotype.Component

@Component
class SignInUseCase(
    private val userRepository: UserRepository,
) {
    private val passwordEncoder = BCryptPasswordEncoder(BCryptVersion.`$2B`)

    fun signIn(
        email: String,
        password: String,
    ): SignInDTO {
        val userEntity =
            userRepository.findByEmail(email)
                ?: throw BusinessException(
                    message = "E-mail or password incorrect",
                    code = "signin.failed",
                )

        if (passwordEncoder.matches(password, userEntity.password).not()) {
            throw BusinessException(
                message = "E-mail or password incorrect",
                code = "signin.failed",
            )
        }

        return SignInDTO(
            id = userEntity.id ?: throw Exception("User ID is null"),
            name = userEntity.name,
            surname = userEntity.surname,
            email = userEntity.email,
            picture = userEntity.picture,
        )
    }
}
