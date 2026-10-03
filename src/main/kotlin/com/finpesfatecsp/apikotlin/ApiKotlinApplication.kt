package com.finpesfatecsp.apikotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.boot.security.autoconfigure.SecurityAutoConfiguration
import org.springframework.boot.security.autoconfigure.UserDetailsServiceAutoConfiguration

@SpringBootApplication(exclude = [SecurityAutoConfiguration::class, UserDetailsServiceAutoConfiguration::class])
class ApiKotlinApplication

fun main(args: Array<String>) {
    runApplication<ApiKotlinApplication>(*args)
}
