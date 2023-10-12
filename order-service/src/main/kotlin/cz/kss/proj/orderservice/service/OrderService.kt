package cz.kss.proj.orderservice.service

interface OrderService {
    suspend fun createOrder()
}