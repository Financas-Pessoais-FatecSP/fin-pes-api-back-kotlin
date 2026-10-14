package com.finpesfatecsp.apikotlin.rest.controller

import com.finpesfatecsp.apikotlin.business.usecase.AccountUseCase
import com.finpesfatecsp.apikotlin.rest.request.CreateAccountRequest
import com.finpesfatecsp.apikotlin.rest.response.AccountResponse
import com.finpesfatecsp.apikotlin.rest.response.CreateAccountResponse
import com.finpesfatecsp.apikotlin.security.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import kotlin.text.substring

@RestController
@RequestMapping("/accounts")
class AccountController(
    private val accountUseCase: AccountUseCase,
    private val jwtService: JwtService,
) {
    @PostMapping
    fun createAccount(
        @RequestBody request: CreateAccountRequest,
        @RequestHeader(name = "Authorization") authorization: String,
    ): ResponseEntity<CreateAccountResponse> {
        val userId = jwtService.getSubject(authorization.substring(7))

        val userAccounts =
            accountUseCase.createAccount(
                userId = userId.toLong(),
                name = request.name,
            )

        return ResponseEntity.created(URI.create("/accounts")).body(
            CreateAccountResponse(
                accounts =
                    userAccounts.map {
                        AccountResponse(
                            id = it.id,
                            name = it.name,
                        )
                    },
            ),
        )
    }
}
