# Kafka Consumer 모듈

## 개요
이 모듈은 Spring Boot와 Spring Kafka를 사용하여 Kafka 메시지를 소비하는 애플리케이션입니다. Kafka 토픽에서 메시지를 구독하고 처리하는 기능을 제공합니다.

## 기술 스택
- Kotlin 1.9.25
- Spring Boot 3.4.5
- Spring Kafka
- JDK 21

## 빌드 방법
프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 모듈을 빌드할 수 있습니다:

```bash
./gradlew consumer:build
```

또는 모듈 디렉토리에서 직접 빌드할 수도 있습니다:

```bash
cd consumer
../gradlew build
```

## 실행 방법
빌드 후 다음 명령어로 애플리케이션을 실행할 수 있습니다:

```bash
./gradlew consumer:bootRun
```

또는 JAR 파일을 직접 실행할 수도 있습니다:

```bash
java -jar consumer/build/libs/consumer-0.0.1-SNAPSHOT.jar
```

## 설정
애플리케이션은 기본적으로 8090 포트에서 실행됩니다. 이 설정은 `application.yml` 파일에서 변경할 수 있습니다.

Kafka 관련 설정을 추가하려면 `application.yml` 파일에 다음과 같은 설정을 추가할 수 있습니다:

```yaml
spring:
  kafka:
    consumer:
      bootstrap-servers: localhost:9092
      group-id: consumer-group
      auto-offset-reset: earliest
      key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
      value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
```

## 사용 예시
Kafka 메시지를 소비하는 리스너 클래스를 다음과 같이 구현할 수 있습니다:

```kotlin
@Component // org.springframework.stereotype.Component
class KafkaConsumerListener {

    @KafkaListener(topics = ["example-topic"], groupId = "consumer-group")
    fun listen(message: String) {
        println("Received message: $message")
        // 메시지 처리 로직 구현
    }
}
```

## 주의사항
- 애플리케이션을 실행하기 전에 Kafka 서버가 실행 중이어야 합니다.
- 토픽이 존재하지 않는 경우 자동으로 생성되도록 설정할 수 있습니다.
