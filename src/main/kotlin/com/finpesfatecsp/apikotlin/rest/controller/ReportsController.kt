package com.finpesfatecsp.apikotlin.rest.controller

import com.finpesfatecsp.apikotlin.business.usecase.ReportsUseCase
import com.finpesfatecsp.apikotlin.rest.response.EntriesByCategoryResponse
import com.finpesfatecsp.apikotlin.rest.response.EntryReportResponse
import com.finpesfatecsp.apikotlin.rest.response.EntryResponse
import com.finpesfatecsp.apikotlin.security.JwtService
import com.finpesfatecsp.apikotlin.utils.converters.EntryConverter
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.format.DateTimeFormatter.ISO_LOCAL_DATE
import java.time.temporal.TemporalAdjusters.firstDayOfMonth
import java.time.temporal.TemporalAdjusters.lastDayOfMonth
import kotlin.collections.map

@RestController
@RequestMapping("/reports")
class ReportsController(
    private val reportsUseCase: ReportsUseCase,
    private val jwtService: JwtService,
) {
    @GetMapping("/entries")
    fun getEntryReport(
        @RequestParam(required = false) from: String?,
        @RequestParam(required = false) to: String?,
        @RequestHeader(name = "Authorization") authorization: String,
    ): ResponseEntity<EntryReportResponse> {
        val userId = jwtService.getSubject(authorization.substring(7))

        val entryReport =
            reportsUseCase.getEntryReportByDate(
                userId = userId.toLong(),
                from = from?.let { LocalDate.parse(it, ISO_LOCAL_DATE) } ?: LocalDate.now().with(firstDayOfMonth()),
                to = to?.let { LocalDate.parse(it, ISO_LOCAL_DATE) } ?: LocalDate.now().with(lastDayOfMonth()),
            )

        return ResponseEntity.ok(
            EntryReportResponse(
                revenues =
                    entryReport.revenues.map {
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
                expenses =
                    entryReport.expenses.map {
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
                revenuesByCategory =
                    entryReport.revenuesByCategory
                        .map { category ->
                            category.key to
                                EntriesByCategoryResponse(
                                    entries = category.value.entries.map { EntryConverter.toResponse(it) },
                                    totalAmount = category.value.totalAmount,
                                )
                        }.toMap(),
                expensesByCategory =
                    entryReport.expensesByCategory
                        .map { category ->
                            category.key to
                                EntriesByCategoryResponse(
                                    entries = category.value.entries.map { EntryConverter.toResponse(it) },
                                    totalAmount = category.value.totalAmount,
                                )
                        }.toMap(),
                totalRevenues = entryReport.totalRevenues,
                totalExpenses = entryReport.totalExpenses,
                balance = entryReport.balance,
            ),
        )
    }
}
