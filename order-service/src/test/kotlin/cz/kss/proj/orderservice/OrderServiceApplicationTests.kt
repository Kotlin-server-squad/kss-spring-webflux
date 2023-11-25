package cz.kss.proj.orderservice

import cz.kss.proj.orderservice.testcontainer.TestContainersConfiguration
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.springframework.test.context.ActiveProfiles

@Import(TestContainersConfiguration::class)
@SpringBootTest
@ActiveProfiles("test")
class OrderServiceApplicationTests {

    @Test
    fun contextLoads() {
    }

}
