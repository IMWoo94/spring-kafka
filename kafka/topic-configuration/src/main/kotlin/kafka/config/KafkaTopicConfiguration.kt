package kafka.config

import org.apache.kafka.clients.admin.NewTopic
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class KafkaTopicConfiguration {
    companion object {
        private val log: Logger = LoggerFactory.getLogger(KafkaTopicConfiguration::class.java)
    }

    @Bean
    fun testTopic(): NewTopic {
        log.info("test topic create")
        return NewTopic("test-topic", 1, 1.toShort())
    }
}