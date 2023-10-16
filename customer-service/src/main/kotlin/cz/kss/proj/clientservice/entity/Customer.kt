package cz.kss.proj.clientservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("customer")
data class Customer(
    @Id
    var id: Long? = null,
    val userName: String,
    val firstName: String,
    val lastName: String,
    val email: String,
    @Transient
    var address: Address?,
    @Transient
    var additionalInformation: AdditionalInformation?,
) {
    companion object {
        const val ID = "id"
        const val USER_NAME = "user_name"
        const val LAST_NAME = "last_name"
        const val FIRST_NAME = "first_name"
        const val EMAIL = "email"
    }
}

fun customerFromRow(prefix: String, row: Map<String, Any>): Customer? {

    return row["${prefix}_${Customer.ID}"]?.let {
        Customer(
            id = it as Long,
            userName = row[Customer.USER_NAME] as String,
            firstName = row[Customer.LAST_NAME] as String,
            lastName = row[Customer.FIRST_NAME] as String,
            email = row[Customer.EMAIL] as String,
            address = addressFromRow("a",row),
            additionalInformation = additionalInformationFromRow("ai",row)
        )
    }
}