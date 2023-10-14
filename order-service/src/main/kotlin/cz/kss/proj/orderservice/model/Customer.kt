package cz.kss.proj.orderservice.model

import org.springframework.data.relational.core.mapping.Table

@Table("customer")
data class Customer(val id: Long?, val name: String, val email: String)