package cz.kss.proj.dataloaderservice.config

import org.slf4j.Logger
import org.slf4j.LoggerFactory.getLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoreConfig {

    @Bean
    fun logger(): Logger {
        return getLogger("customLogger")
    }
}