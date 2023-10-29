package cz.kss.proj.customerservice.config.route

import cz.kss.proj.customerservice.handler.CustomerHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.MediaType.APPLICATION_JSON
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class CustomerRouter(
    private val customerHandler: CustomerHandler,
) {
    @Bean
    fun apiRouter() = coRouter {
        "/api/customer".nest {
            accept(APPLICATION_JSON).nest {
                contentType(APPLICATION_JSON).nest {
                    GET("", customerHandler::getCustomer)
                    POST("", customerHandler::createCustomer)
                }
            }
        }
    }
}