package com.finpesfatecsp.apikotlin.rest.controller

import com.finpesfatecsp.apikotlin.business.usecase.EntryCategoryUseCase
import com.finpesfatecsp.apikotlin.business.usecase.dto.EntryCategoryDTO
import com.finpesfatecsp.apikotlin.business.usecase.dto.enums.EntryType
import com.finpesfatecsp.apikotlin.rest.request.CreateEntryCategoryRequest
import com.finpesfatecsp.apikotlin.rest.response.EntryCategoryResponse
import com.finpesfatecsp.apikotlin.rest.response.FindUserCategoriesResponse
import com.finpesfatecsp.apikotlin.security.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI

@RestController
@RequestMapping("/entries/categories")
class EntryCategoryController(
    private val entryCategoryUseCase: EntryCategoryUseCase,
    private val jwtService: JwtService,
) {
    @PostMapping
    fun createEntryCategory(
        @RequestBody request: CreateEntryCategoryRequest,
        @RequestHeader(name = "Authorization") authorization: String,
    ): ResponseEntity<EntryCategoryResponse> {
        val userId = jwtService.getSubject(authorization.substring(7))

        val entryCategory =
            entryCategoryUseCase.createEntryCategory(
                EntryCategoryDTO(
                    id = null,
                    userId = userId.toLong(),
                    name = request.name,
                    type = EntryType.fromDescription(request.type),
                ),
            )

        return ResponseEntity.created(URI.create("/entries/categories")).body(
            EntryCategoryResponse(
                id = entryCategory.id ?: throw Exception("Category entry ID is null"),
                name = entryCategory.name,
                type = entryCategory.type.description,
            ),
        )
    }

    @GetMapping
    fun findByUserId(
        @RequestHeader(name = "Authorization") authorization: String,
    ): ResponseEntity<FindUserCategoriesResponse> {
        val userId = jwtService.getSubject(authorization.substring(7))

        val userCategories =
            entryCategoryUseCase.findByUserId(
                userId = userId.toLong(),
            )

        return ResponseEntity.ok(
            FindUserCategoriesResponse(
                categories =
                    userCategories.map {
                        EntryCategoryResponse(
                            id = it.id ?: throw Exception("Category entry ID is null"),
                            name = it.name,
                            type = it.type.description,
                        )
                    },
            ),
        )
    }

    @DeleteMapping("/{categoryId}")
    fun deleteEntryCategory(
        @PathVariable categoryId: Long,
        @RequestHeader(name = "Authorization") authorization: String,
    ): ResponseEntity<Nothing> {
        val userId = jwtService.getSubject(authorization.substring(7))

        entryCategoryUseCase.deleteById(userId.toLong(), categoryId)

        return ResponseEntity.noContent().build()
    }
}
