package com.example.sub1.model.entity

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document

@Document
class Product(
    @Id
    var id: String? = null,

    @Indexed
    var productId: String? = null,
    var available: Boolean? = false,
    var description: String? = null,
    var price: Double? = 0.0,
    var amount: Int? = 0
)
