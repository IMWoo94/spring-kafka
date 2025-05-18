package consumer.listener

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
class KafkaConsumerListener {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(KafkaConsumerListener::class.java)
    }

    @KafkaListener(
        topics = ["test-topic"],
        groupId = "test-group",
    )
    fun consumer(message: String) {
        log.info("Received message: $message")
    }
}