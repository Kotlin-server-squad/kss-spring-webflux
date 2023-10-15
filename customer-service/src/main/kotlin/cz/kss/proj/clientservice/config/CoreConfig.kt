package cz.kss.proj.clientservice.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoreConfig {
    @Bean
    fun logger(): Logger {
        return LoggerFactory.getLogger("customLogger")
    }
}