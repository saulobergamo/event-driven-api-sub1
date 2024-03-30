package com.example.sub1.listener

import com.example.sub1.model.ProductRequest
import com.example.sub1.service.SubService
import com.fasterxml.jackson.databind.ObjectMapper
import mu.KotlinLogging
import org.springframework.amqp.rabbit.annotation.RabbitHandler
import org.springframework.amqp.rabbit.annotation.RabbitListener
import org.springframework.stereotype.Component

@Component
@RabbitListener(queues = ["\${spring.rabbitmq.name.queue}"])
class RabbitMQListener(
    private val objectMapper: ObjectMapper,
    private val subService: SubService
) {
    private val logger = KotlinLogging.logger {}

    @RabbitHandler
    fun readFromQueue(message: String) {
        val product = objectMapper.readValue(message, ProductRequest::class.java)
        subService.processMessage(product).also {
            logger.info { "readFromQueue: message successfully read from queue - message=$message" }
        }
    }
}
