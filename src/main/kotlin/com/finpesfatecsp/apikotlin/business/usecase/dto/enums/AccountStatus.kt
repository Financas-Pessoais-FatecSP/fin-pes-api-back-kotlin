package com.finpesfatecsp.apikotlin.business.usecase.dto.enums

enum class AccountStatus(
    val description: String,
) {
    ACTIVE("ATIVA"),
    INACTIVE("INATIVA"),
    ;

    companion object {
        fun fromDescription(description: String): AccountStatus =
            when (description) {
                "ATIVA" -> ACTIVE
                "INATIVA" -> INACTIVE
                else -> throw IllegalArgumentException("No enum constant with description $description")
            }
    }
}
