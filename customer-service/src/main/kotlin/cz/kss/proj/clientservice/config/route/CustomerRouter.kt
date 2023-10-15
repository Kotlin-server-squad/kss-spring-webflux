package cz.kss.proj.clientservice.config.route

import cz.kss.proj.clientservice.handler.CustomerHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class CustomerRouter(
    private val customerHandler: CustomerHandler,
) {
    @Bean
    fun apiRouter() = coRouter {
        "/api/customer".nest {
            accept(APPLICATION_JSON).nest{
                GET("",)
                contentType(APPLICATION_JSON).nest {
                    POST("", customerHandler::createCustomer)
                }
            }
        }
    }
}