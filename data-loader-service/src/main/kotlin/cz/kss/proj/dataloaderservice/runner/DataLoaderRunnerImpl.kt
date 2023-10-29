package cz.kss.proj.dataloaderservice.runner

import cz.kss.proj.dataloaderservice.dto.AdditionalInformation
import cz.kss.proj.dataloaderservice.dto.Address
import cz.kss.proj.dataloaderservice.dto.Customer
import cz.kss.proj.dataloaderservice.dto.Gender
import cz.kss.proj.dataloaderservice.service.CustomerService
import io.github.serpro69.kfaker.Faker
import io.github.serpro69.kfaker.FakerConfig
import io.github.serpro69.kfaker.create
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.stereotype.Component
import kotlin.random.Random

@Component
class DataLoaderRunnerImpl(
    private val customerService: CustomerService,
    private val defaultScope: CoroutineScope
) : ApplicationRunner {
    override fun run(args: ApplicationArguments?) {
        defaultScope.launch {
            customerService.createCustomer(generateFakeCustomer())
        }
    }

    fun generateFakeCustomer(): Customer {
        val faker = Faker(FakerConfig.builder().create {
            this.locale = "en"
        })
        faker.name
        val firstName = faker.name.firstName()
        val lastName = faker.name.lastName()
        val address = generateFakeAddress()
        val email = faker.internet.email()
        val userName = firstName + lastName
        val additionalInformation = generateFakeAdditionalInformation()

        return Customer(userName, firstName, lastName, address, email, additionalInformation)
    }

    fun generateFakeAddress(): Address {
        val faker = Faker(FakerConfig.builder().create {
            this.locale = "en"
        })

        val zipCode = faker.random.nextInt(10000, 99999)
        val street = faker.address.streetAddress()
        val town = faker.address.city()
        val country = faker.address.country()
        val region = faker.address.state()

        return Address(zipCode, street, town, country, region)
    }

    fun generateFakeAdditionalInformation(): AdditionalInformation {
        val faker = Faker(FakerConfig.builder().create {
            this.locale = "en"
        })

        val birthDate = faker.person.birthDate(faker.random.nextInt(18, 90).toLong())

        val birthDay = birthDate.dayOfMonth
        val birthMonth = birthDate.monthValue
        val birthYear = birthDate.year
        val gender = getRandomGender()

        return AdditionalInformation(birthDay, birthMonth, birthYear, gender)
    }

    fun getRandomGender(): Gender {
        val values = Gender.entries.toTypedArray()
        val randomIndex = Random.nextInt(values.size)
        return values[randomIndex]
    }
}