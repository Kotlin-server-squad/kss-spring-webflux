package cz.kss.proj.dataloaderservice.config

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class CustomerConfig {

    @Value("\${customer.base.url}")
    private lateinit var baseUrl: String

    @Bean
    fun customerWebClient(webClientBuilder: WebClient.Builder): WebClient {
        return webClientBuilder
            .baseUrl(baseUrl)
            .build()
    }
}