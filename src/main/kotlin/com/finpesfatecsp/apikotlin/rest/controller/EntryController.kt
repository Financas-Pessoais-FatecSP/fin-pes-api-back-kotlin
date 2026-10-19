package com.finpesfatecsp.apikotlin.rest.controller

import com.finpesfatecsp.apikotlin.business.usecase.EntryUseCase
import com.finpesfatecsp.apikotlin.business.usecase.dto.EntryDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.EntryType
import com.finpesfatecsp.apikotlin.rest.request.CreateEntryRequest
import com.finpesfatecsp.apikotlin.rest.response.AccountWithEntriesResponse
import com.finpesfatecsp.apikotlin.rest.response.CreateEntryResponse
import com.finpesfatecsp.apikotlin.rest.response.EntryResponse
import com.finpesfatecsp.apikotlin.security.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/entries")
class EntryController(
    private val entryUseCase: EntryUseCase,
    private val jwtService: JwtService,
) {
    @PostMapping
    fun createEntry(
        @RequestBody request: CreateEntryRequest,
        @RequestHeader(name = "Authorization") authorization: String,
    ): ResponseEntity<CreateEntryResponse> {
        val userId = jwtService.getSubject(authorization.substring(7))

        val accountWithEntries =
            entryUseCase.createEntry(
                userId = userId.toLong(),
                entry =
                    EntryDTO(
                        id = null,
                        accountId = request.accountId,
                        categoryId = request.categoryId,
                        type = EntryType.fromDescription(request.type),
                        description = request.description,
                        amount = request.amount,
                        date = request.date,
                        details = request.details,
                    ),
            )

        return ResponseEntity.created(URI.create("/teste")).body(
            CreateEntryResponse(
                id = accountWithEntries.id,
                name = accountWithEntries.name,
                entries =
                    accountWithEntries.entries.map {
                        EntryResponse(
                            id = it.id ?: throw Exception("Entry ID is null"),
                            accountId = it.accountId,
                            categoryId = it.categoryId,
                            type = it.type.description,
                            description = it.description,
                            amount = it.amount,
                            date = it.date,
                            details = it.details,
                        )
                    },
            ),
        )
    }

    @GetMapping
    fun findByAccountId(
        @RequestParam accountId: Long,
        @RequestHeader(name = "Authorization") authorization: String,
    ): ResponseEntity<AccountWithEntriesResponse> {
        val userId = jwtService.getSubject(authorization.substring(7))

        val accountEntries = entryUseCase.findByAccountId(userId.toLong(), accountId)

        return ResponseEntity.ok(
            AccountWithEntriesResponse(
                id = accountEntries.id,
                name = accountEntries.name,
                entries =
                    accountEntries.entries.map {
                        EntryResponse(
                            id = it.id ?: throw Exception("Entry ID is null"),
                            accountId = it.accountId,
                            categoryId = it.categoryId,
                            type = it.type.description,
                            description = it.description,
                            amount = it.amount,
                            date = it.date,
                            details = it.details,
                        )
                    },
            ),
        )
    }
}
