package cz.kss.proj.customerservice.config

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import kotlin.coroutines.CoroutineContext

@Configuration
class CoroutineConfig {

    @Bean
    fun defaultCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Default)

    @Bean
    fun analyticsCoroutineScope(): CoroutineScope = CoroutineScope(Dispatchers.Default)

    @Bean
    fun defaultCoroutineContext(): CoroutineContext = Dispatchers.Default
}