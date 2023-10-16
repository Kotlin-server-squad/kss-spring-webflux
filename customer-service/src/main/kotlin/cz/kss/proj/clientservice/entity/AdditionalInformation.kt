package cz.kss.proj.clientservice.entity

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table

@Table("additional_information")
data class AdditionalInformation(
    @Id
    var id: Long? = null,
    val birthDay: Int,
    val birthMonth: Int,
    val birthYear: Int,
    val gender: Gender,
){
    companion object{
        const val ID = "id"
        const val BIRTH_DAY = "birth_day"
        const val BIRTH_MONTH = "birth_month"
        const val BIRTH_YEAR = "birth_year"
        const val GENDER = "gender"
    }
}

fun additionalInformationFromRow(prefix: String, row: Map<String,Any>): AdditionalInformation?{
    return row["${prefix}_${AdditionalInformation.ID}"]?.let {
        AdditionalInformation(
            id = it as Long,
            birthDay = row[AdditionalInformation.BIRTH_DAY] as Int,
            birthMonth = row[AdditionalInformation.BIRTH_MONTH] as Int,
            birthYear = row[AdditionalInformation.BIRTH_YEAR] as Int,
            gender = Gender.valueOf(row[AdditionalInformation.GENDER] as String),
        )
    }
}

enum class Gender{
    MALE,FEMALE,NON
}