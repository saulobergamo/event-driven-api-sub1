package com.example.sub1.config

import org.springframework.amqp.core.Binding
import org.springframework.amqp.core.BindingBuilder
import org.springframework.amqp.core.FanoutExchange
import org.springframework.amqp.core.Queue
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class RabbitMQConfig {

    @Value("\${spring.rabbitmq.name.queue}")
    private lateinit var queue: String

    @Value("\${spring.rabbitmq.name.exchange}")
    private lateinit var exchange: String

//    in case we have a direct exchange its necessary to configure
//    a routing key to bind a queue to a specific exchange
//    @Value("\${spring.rabbitmq.queue.routing.key}")
//    private lateinit var routingKey : String

    @Bean
    fun fanoutExchange(): FanoutExchange {
        return FanoutExchange(exchange)
    }
    @Bean
    fun queue1(): Queue {
        return Queue(queue)
    }

    @Bean
    fun bindingQueue1ToFanoutExchange(): Binding {
        return BindingBuilder.bind(queue1()).to(fanoutExchange())
    }
}
