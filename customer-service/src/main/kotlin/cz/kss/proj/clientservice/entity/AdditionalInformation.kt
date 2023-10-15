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
//    fromRow(row: Map<String,Any>): Add{
//
//    }
}

enum class Gender{
    MALE,FEMALE,NON
}