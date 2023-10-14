package cz.kss.proj.orderservice.config

import io.r2dbc.h2.H2ConnectionFactory
import io.r2dbc.spi.ConnectionFactory
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Primary
import org.springframework.context.annotation.Profile

@TestConfiguration
class TestConfig {

    @Bean
    @Primary
    @Profile("test")
    fun connectionFactory(): ConnectionFactory {
        return H2ConnectionFactory.inMemory("testdb")
    }
}