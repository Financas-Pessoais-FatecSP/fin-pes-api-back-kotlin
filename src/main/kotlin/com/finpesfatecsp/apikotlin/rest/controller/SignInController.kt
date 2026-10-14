package com.finpesfatecsp.apikotlin.rest.controller

import com.finpesfatecsp.apikotlin.business.usecase.SignInUseCase
import com.finpesfatecsp.apikotlin.rest.request.SignInRequest
import com.finpesfatecsp.apikotlin.rest.response.SignInResponse
import com.finpesfatecsp.apikotlin.security.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class SignInController(
    private val signInUseCase: SignInUseCase,
    private val jwtService: JwtService,
) {
    @PostMapping("/signin")
    fun signIn(
        @RequestBody request: SignInRequest,
    ): ResponseEntity<SignInResponse> {
        val user =
            signInUseCase.signIn(
                email = request.email,
                password = request.password,
            )

        val token = jwtService.generateToken(user.id.toString(), user.email)

        return ResponseEntity.ok(
            user.let {
                SignInResponse(
                    name = it.name,
                    surname = it.surname,
                    picture = it.picture,
                    token = token,
                )
            },
        )
    }
}
