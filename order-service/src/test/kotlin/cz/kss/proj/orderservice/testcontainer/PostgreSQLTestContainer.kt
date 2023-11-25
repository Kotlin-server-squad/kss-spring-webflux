package cz.kss.proj.orderservice.testcontainer

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName

@Import(PostgreSQLTestContainerConfiguration::class)
annotation class PostgreSQLTestContainer

@TestConfiguration(proxyBeanMethods = false)
class PostgreSQLTestContainerConfiguration {

    @ServiceConnection
    @Bean
    fun postgreSQLContainer() = PostgreSQLContainer(DockerImageName.parse("postgres:16.1"))
            .withReuse(true)
}