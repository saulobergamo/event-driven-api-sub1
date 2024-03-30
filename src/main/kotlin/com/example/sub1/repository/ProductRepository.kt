package com.example.sub1.repository

import com.example.sub1.model.entity.Product
import org.springframework.data.mongodb.repository.MongoRepository

interface ProductRepository : MongoRepository<Product, String> {
    fun getProductByProductId(productId: String): Product?
}
