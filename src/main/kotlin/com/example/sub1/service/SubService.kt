package com.example.sub1.service

import com.example.sub1.model.ProductRequest
import com.example.sub1.model.entity.Product
import com.example.sub1.repository.ProductRepository
import mu.KotlinLogging
import org.springframework.stereotype.Service

@Service
class SubService(
    private val productRepository: ProductRepository
) {
    private val logger = KotlinLogging.logger {}

    fun processMessage(productRequest: ProductRequest) {
        val mongoProduct = productRepository.getProductByProductId(productRequest.productId)

        val newProduct = setNewProduct(mongoProduct, productRequest)

        productRepository.save(newProduct).also {
            logger.info {
                "processMessage: stock updated for product=${newProduct.description}"
            }
        }
    }

    private fun setNewProduct(
        mongoProduct: Product?,
        productRequest: ProductRequest
    ): Product {
        val newProduct = Product()

        if (mongoProduct != null) {
            if (stillAvailable(mongoProduct.amount, productRequest.amount)) {
                newProduct.apply {
                    this.id = mongoProduct.id
                    this.productId = productRequest.productId
                    this.available = true
                    this.description = productRequest.description
                    this.price = mongoProduct.price
                    this.amount = productRequest.amount?.let { (mongoProduct.amount)?.minus(it) }
                }
            } else {
                newProduct.apply {
                    this.id = mongoProduct.id
                    this.productId = productRequest.productId
                    this.available = false
                    this.description = productRequest.description
                    this.price = mongoProduct.price
                    this.amount = productRequest.amount?.let { (mongoProduct.amount)?.minus(it) }
                }
            }
        }
        return newProduct
    }

    private fun stillAvailable(
        mongoProductAmount: Int?,
        messageProductAmount: Int?
    ): Boolean = (
        mongoProductAmount?.let { mongoProductAmount ->
            messageProductAmount?.let { messageProductAmount ->
                mongoProductAmount.minus(messageProductAmount) > 0
            }
        } ?: false
        )
}
