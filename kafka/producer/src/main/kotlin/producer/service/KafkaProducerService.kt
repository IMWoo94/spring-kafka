package producer.service

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class KafkaProducerService(
    private val kafkaTestTemplate: KafkaTemplate<String, String>,
) {
    fun sendMessage(topic: String, message: String) {
        kafkaTestTemplate.send(topic, message)
    }
}