package cz.kss.proj.dataloaderservice

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class DataLoaderServiceApplication

fun main(args: Array<String>) {
    runApplication<DataLoaderServiceApplication>(*args)
}
