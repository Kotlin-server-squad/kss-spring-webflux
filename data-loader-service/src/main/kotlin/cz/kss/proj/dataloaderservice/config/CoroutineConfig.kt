package cz.kss.proj.dataloaderservice.config

import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import org.slf4j.Logger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class CoroutineConfig(private val logger: Logger) {

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, e -> logger.warn("Execution failed", e) }

    @Bean
    fun defaultScope(): CoroutineScope = CoroutineScope(Dispatchers.IO + SupervisorJob() + coroutineExceptionHandler)

}