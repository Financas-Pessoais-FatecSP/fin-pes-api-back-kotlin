package com.finpesfatecsp.apikotlin.business.usecase.dto.enums

enum class EntryType(
    val description: String,
) {
    REVENUE("RECEITA"),
    EXPENSE("DESPESA"),
    ;

    companion object {
        fun fromDescription(description: String): EntryType =
            when (description) {
                "RECEITA" -> REVENUE
                "DESPESA" -> EXPENSE
                else -> throw IllegalArgumentException("No enum constant with description $description")
            }
    }
}
