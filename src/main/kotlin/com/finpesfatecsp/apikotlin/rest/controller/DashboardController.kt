package com.finpesfatecsp.apikotlin.rest.controller

import com.finpesfatecsp.apikotlin.business.usecase.DashboardUseCase
import com.finpesfatecsp.apikotlin.rest.response.EntryResponse
import com.finpesfatecsp.apikotlin.rest.response.FinancialSummaryResponse
import com.finpesfatecsp.apikotlin.security.JwtService
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

@RestController
@RequestMapping("/dashboards")
class DashboardController(
    private val dashboardUseCase: DashboardUseCase,
    private val jwtService: JwtService,
) {
    @GetMapping("/financial-summary")
    fun getFinancialSummary(
        @RequestParam(required = false) from: String?,
        @RequestParam(required = false) to: String?,
        @RequestHeader(name = "Authorization") authorization: String,
    ): ResponseEntity<FinancialSummaryResponse> {
        val userId = jwtService.getSubject(authorization.substring(7))

        val financialSummary =
            dashboardUseCase.getFinancialSummary(
                userId = userId.toLong(),
                from = from?.let { LocalDate.parse(it, ISO_LOCAL_DATE) } ?: LocalDate.now().with(firstDayOfMonth()),
                to = to?.let { LocalDate.parse(it, ISO_LOCAL_DATE) } ?: LocalDate.now().with(lastDayOfMonth()),
            )

        return ResponseEntity.ok(
            FinancialSummaryResponse(
                revenues =
                    financialSummary.revenues.map {
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
                    financialSummary.expenses.map {
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
                revenuesByCategory = financialSummary.revenuesByCategory,
                expensesByCategory = financialSummary.expensesByCategory,
                totalRevenues = financialSummary.totalRevenues,
                totalExpenses = financialSummary.totalExpenses,
                balance = financialSummary.balance,
            ),
        )
    }
}
