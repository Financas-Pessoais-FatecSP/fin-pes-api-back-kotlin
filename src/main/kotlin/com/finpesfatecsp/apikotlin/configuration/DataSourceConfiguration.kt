package com.finpesfatecsp.apikotlin.configuration

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.sql.DataSource

@Configuration
class DataSourceConfiguration(
    @Value("\${spring.datasource.username}")
    private val dataSourceUsername: String,
    @Value("\${spring.datasource.password}")
    private val dataSourcePassword: String,
    @Value("\${spring.datasource.url}")
    private val dataSourceUrl: String,
) {
    @Bean
    fun dataSource(): DataSource =
        DataSourceBuilder
            .create()
            .url(dataSourceUrl)
            .username(dataSourceUsername)
            .password(dataSourcePassword)
            .driverClassName("org.postgresql.Driver")
            .build()
}
