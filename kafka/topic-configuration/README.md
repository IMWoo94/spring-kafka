# Kafka Topic Configuration 모듈

## 개요

이 모듈은 Spring Boot와 Spring Kafka를 사용하여 Kafka 토픽을 프로그래밍 방식으로 생성하고 관리하는 라이브러리입니다. 애플리케이션 시작 시 필요한 Kafka 토픽이 자동으로 생성되도록 설정할 수 있습니다.

## 기술 스택

- Kotlin 1.9.25
- Spring Boot 3.4.5
- Spring Kafka 3.3.5
- JDK 21

## 빌드 방법

프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 모듈을 빌드할 수 있습니다:

```bash
./gradlew topic-configuration:build
```

또는 모듈 디렉토리에서 직접 빌드할 수도 있습니다:

```bash
cd topic-configuration
../gradlew build
```

## 사용 방법

이 모듈은 독립 실행형 애플리케이션이 아닌 라이브러리로 설계되었습니다. 다른 Spring Boot 애플리케이션에서 의존성으로 추가하여 사용할 수 있습니다.

### 의존성 추가

다른 모듈의 build.gradle.kts 파일에 다음과 같이 의존성을 추가합니다:

```kotlin
dependencies {
    implementation(project(":topic-configuration"))
}
```

### 토픽 설정 사용

이 모듈은 Spring의 자동 구성 기능을 통해 Kafka 토픽을 생성합니다. 현재 다음과 같은 토픽이 자동으로 생성됩니다:

- `test-topic`: 파티션 1개, 복제 팩터 1

## 커스텀 토픽 추가 방법

새로운 Kafka 토픽을 추가하려면 `KafkaTopicConfiguration` 클래스에 새로운 Bean 메서드를 추가하면 됩니다:

```kotlin
@Bean
fun customTopic(): NewTopic {
    return NewTopic("custom-topic", 3, 2.toShort())
}
```

위 예시는 파티션 3개, 복제 팩터 2를 가진 "custom-topic"이라는 새 토픽을 생성합니다.

## 설정

기본적으로 이 모듈은 로컬 Kafka 서버(localhost:9092)에 연결됩니다. 다른 Kafka 서버에 연결하려면 애플리케이션의 `application.yml` 파일에 다음과 같은 설정을 추가하세요:

```yaml
spring:
  kafka:
    bootstrap-servers: your-kafka-server:9092
```

## 주의사항

- 이 모듈을 사용하는 애플리케이션을 실행하기 전에 Kafka 서버가 실행 중이어야 합니다.
- 토픽 생성 권한이 있는 사용자로 Kafka에 연결해야 합니다.
- 이미 존재하는 토픽과 동일한 이름의 토픽을 생성하려고 하면, 기존 토픽의 설정이 변경되지 않습니다(Kafka의 기본 동작).