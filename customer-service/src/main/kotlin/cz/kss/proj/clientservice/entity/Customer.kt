package cz.kss.proj.clientservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Column
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
//    fun fromRow(row: HashMap<String, Any>): Customer {
//        return Customer(
//            id = row["id"] as Long,
//            userName = row["user_name"] as String,
//            firstName = row["first_name"] as String,
//            lastName = row["last_name"] as String,
//            addressId = row["address_id"] as Long,
//            address =,
//            additionalInformationId = row["additional_information_id"] as Long,
//            additionalInformation =,
//            email = row["email"] as String,
//        )
//    }
}