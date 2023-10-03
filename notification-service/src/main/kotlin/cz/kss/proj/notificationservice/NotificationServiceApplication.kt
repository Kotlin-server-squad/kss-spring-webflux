package cz.kss.proj.notificationservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.r2dbc.R2dbcAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [R2dbcAutoConfiguration::class])
class NotificationServiceApplication

fun main(args: Array<String>) {
    runApplication<NotificationServiceApplication>(*args)
}
