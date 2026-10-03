package com.finpesfatecsp.apikotlin.rest.controller

import com.finpesfatecsp.apikotlin.rest.controller.request.SignUpRequest
import com.finpesfatecsp.apikotlin.rest.controller.response.SignUpResponse
import com.finpesfatecsp.apikotlin.usecase.SignUpDTO
import com.finpesfatecsp.apikotlin.usecase.SignUpUseCase
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SignUpController(
    private val signUpUseCase: SignUpUseCase,
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

        return ResponseEntity.ok(
            user.let {
                SignUpResponse(
                    id = it.id ?: throw Exception("User ID is null"),
                    name = it.name,
                    surname = it.surname,
                    email = it.email,
                    password = it.password,
                    picture = it.picture,
                )
            },
        )
    }
}
