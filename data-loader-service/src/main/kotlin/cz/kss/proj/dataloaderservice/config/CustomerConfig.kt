package cz.kss.proj.dataloaderservice.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.client.WebClient

@Configuration
class CustomerConfig {
    @Bean
    fun customerWebClient(webClientBuilder: WebClient.Builder): WebClient {
        return webClientBuilder
            .baseUrl("http://localhost:8881")
            .build()
    }
}