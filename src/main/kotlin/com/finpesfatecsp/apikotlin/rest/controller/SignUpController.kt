package com.finpesfatecsp.apikotlin.rest.controller

import com.finpesfatecsp.apikotlin.business.usecase.SignUpUseCase
import com.finpesfatecsp.apikotlin.business.usecase.dto.SignUpDTO
import com.finpesfatecsp.apikotlin.rest.request.SignUpRequest
import com.finpesfatecsp.apikotlin.rest.response.SignUpResponse
import com.finpesfatecsp.apikotlin.security.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SignUpController(
    private val signUpUseCase: SignUpUseCase,
    private val jwtService: JwtService,
) {
    @PostMapping("/signup")
    fun signUp(
        @RequestBody request: SignUpRequest,
    ): ResponseEntity<SignUpResponse> {
        val user =
            signUpUseCase.signUp(
                SignUpDTO(
                    name = request.name,
                    surname = request.surname,
                    email = request.email,
                    password = request.password,
                    picture = request.picture,
                ),
            )

        val token = jwtService.generateToken(user.id.toString(), user.email)

        return ResponseEntity.ok(
            user.let {
                SignUpResponse(
                    name = it.name,
                    surname = it.surname,
                    picture = it.picture,
                    token = token,
                )
            },
        )
    }
}
