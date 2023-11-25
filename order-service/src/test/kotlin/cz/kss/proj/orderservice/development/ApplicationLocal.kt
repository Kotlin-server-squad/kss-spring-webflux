package cz.kss.proj.orderservice.development

import cz.kss.proj.orderservice.main
import cz.kss.proj.orderservice.testcontainer.PostgreSQLTestContainer
import org.springframework.boot.SpringApplication

fun main(args: Array<String>) {
    System.setProperty("spring.profiles.active", "local")
    SpringApplication
            .from(::main)
            .with(TestContainersConfiguration::class.java)
            .run(*args)
}

@PostgreSQLTestContainer
private class TestContainersConfiguration
