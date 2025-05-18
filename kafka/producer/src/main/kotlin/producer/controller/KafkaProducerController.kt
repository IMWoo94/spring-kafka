package producer.controller

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import producer.service.KafkaProducerService

@RestController
@RequestMapping("/api/kafka")
class KafkaController(
    private val producerService: KafkaProducerService
) {
    @PostMapping("/publish")
    fun publishMessage(@RequestBody message: String): ResponseEntity<String> {
        producerService.sendMessage("test-topic", message)
        return ResponseEntity.ok("메시지가 성공적으로 발행되었습니다.")
    }
}