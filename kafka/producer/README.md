# Kafka Producer 모듈

## 개요
이 모듈은 Spring Boot와 Spring Kafka를 사용하여 Kafka 메시지를 생성하고 전송하는 애플리케이션입니다. 데이터를 Kafka 토픽으로 발행하는 기능을 제공합니다.

## 기술 스택
- Kotlin 1.9.25
- Spring Boot 3.4.5
- Spring Kafka
- JDK 21

## 빌드 방법
프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 모듈을 빌드할 수 있습니다:

```bash
./gradlew producer:build
```

또는 모듈 디렉토리에서 직접 빌드할 수도 있습니다:

```bash
cd producer
../gradlew build
```

## 실행 방법
빌드 후 다음 명령어로 애플리케이션을 실행할 수 있습니다:

```bash
./gradlew producer:bootRun
```

또는 JAR 파일을 직접 실행할 수도 있습니다:

```bash
java -jar producer/build/libs/producer-0.0.1-SNAPSHOT.jar
```

## 설정
애플리케이션은 기본적으로 8070 포트에서 실행됩니다. 이 설정은 `application.yml` 파일에서 변경할 수 있습니다.

Kafka 관련 설정을 추가하려면 `application.yml` 파일에 다음과 같은 설정을 추가할 수 있습니다:

```yaml
spring:
  kafka:
    producer:
      bootstrap-servers: localhost:9092
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

## 사용 예시
Kafka 메시지를 생성하고 전송하는 서비스 클래스를 다음과 같이 구현할 수 있습니다:

```kotlin
@Service // org.springframework.stereotype.Service
class KafkaProducerService(
    private val kafkaTemplate: KafkaTemplate<String, String>
) {

    fun sendMessage(topic: String, message: String) {
        kafkaTemplate.send(topic, message)
            .addCallback(
                { result -> println("메시지 전송 성공: ${result?.recordMetadata?.offset()}") },
                { ex -> println("메시지 전송 실패: ${ex.message}") }
            )
    }
}
```

REST API를 통해 메시지를 전송하는 컨트롤러 예시:

```kotlin
@RestController // org.springframework.web.bind.annotation.RestController
@RequestMapping("/api/kafka") // org.springframework.web.bind.annotation.RequestMapping
class KafkaController(
    private val producerService: KafkaProducerService
) {

    @PostMapping("/publish") // org.springframework.web.bind.annotation.PostMapping
    fun publishMessage(@RequestBody message: String): ResponseEntity<String> {
        producerService.sendMessage("example-topic", message)
        return ResponseEntity.ok("메시지가 성공적으로 발행되었습니다.")
    }
}
```

## 주의사항
- 애플리케이션을 실행하기 전에 Kafka 서버가 실행 중이어야 합니다.
- 토픽이 존재하지 않는 경우 자동으로 생성되도록 설정할 수 있습니다.
- 대용량 메시지 처리 시 배치 처리 설정을 고려하세요.