package cz.kss.proj.orderservice.testcontainer

import org.springframework.boot.test.context.TestConfiguration
import org.springframework.boot.testcontainers.service.connection.ServiceConnection
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.core.env.Environment
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.utility.DockerImageName
import org.testcontainers.utility.TestcontainersConfiguration
import java.util.UUID

@Import(PostgreSQLTestContainerConfiguration::class)
annotation class PostgreSQLTestContainer

@TestConfiguration(proxyBeanMethods = false)
private class PostgreSQLTestContainerConfiguration {

    @ServiceConnection
    @Bean
    fun postgreSQLContainer(environment: Environment): PostgreSQLContainer<*> =
            PostgreSQLContainer(DockerImageName.parse("postgres:16.1"))
                    .withCreateContainerCmdModifier { cmd ->
                        cmd.withName("testcontainers-postgres-${getProfilesString(environment)}-${UUID.randomUUID()}")
                    }
                    .withEnv("PROFILES", getProfilesString(environment))
                    .withReuse(TestcontainersConfiguration.getInstance().environmentSupportsReuse())
}

private fun getProfilesString(environment: Environment) =
        environment.activeProfiles.sorted().joinToString(",")
