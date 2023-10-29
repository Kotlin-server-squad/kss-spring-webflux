package cz.kss.proj.customerservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("address")
data class Address(
    @Id
    var id: Long? = null,
    val zipCode: Int,
    val street: String,
    val town: String,
    val country: String,
    val region: String,
) {
    companion object {
         const val ID = "id"
         const val ZIP_CODE = "zip_code"
         const val STREET = "street"
         const val COUNTRY = "country"
         const val TOWN = "town"
         const val REGION = "region"

    }
}

fun addressFromRow(prefix: String, row: Map<String, Any>): Address? {
    return row["${prefix}_${Address.ID}"]?.let {
        Address(
            id = it as Long,
            zipCode = row[Address.ZIP_CODE] as Int,
            street = row[Address.STREET] as String,
            town = row[Address.TOWN] as String,
            country = row[Address.COUNTRY] as String,
            region = row[Address.REGION] as String,
        )
    }
}
