package cz.kss.proj.orderservice.repository

import cz.kss.proj.orderservice.model.Item
import cz.kss.proj.orderservice.model.ItemCategory
import cz.kss.proj.orderservice.model.Order
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ItemCategoryRepository: CoroutineCrudRepository<ItemCategory, Long>{
}