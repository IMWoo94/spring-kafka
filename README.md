# Spring Kafka 멀티 모듈 프로젝트

## 개요
이 프로젝트는 Spring Boot와 Spring Kafka를 사용하여 Kafka 메시지 생산자(Producer)와 소비자(Consumer)를 구현한 멀티 모듈 애플리케이션입니다. Kafka를 통한 메시지 기반 통신 시스템의 기본 구조를 제공합니다.

## 프로젝트 구조
이 프로젝트는 다음과 같은 모듈로 구성되어 있습니다:

- **producer**: Kafka 메시지를 생성하고 전송하는 모듈
- **consumer**: Kafka 토픽에서 메시지를 구독하고 처리하는 모듈

각 모듈은 독립적으로 실행 가능한 Spring Boot 애플리케이션입니다.

## 기술 스택
- Kotlin 1.9.25
- Spring Boot 3.4.5
- Spring Kafka
- JDK 21
- Gradle (멀티 모듈 빌드)

## 사전 요구사항
- JDK 21
- Gradle 7.6 이상
- Kafka 서버 (로컬 또는 원격)

## 빌드 방법
전체 프로젝트를 빌드하려면 프로젝트 루트 디렉토리에서 다음 명령어를 실행하세요:

```bash
./gradlew build
```

특정 모듈만 빌드하려면 다음 명령어를 사용하세요:

```bash
./gradlew producer:build  # Producer 모듈만 빌드
./gradlew consumer:build  # Consumer 모듈만 빌드
```

## 실행 방법
각 모듈은 독립적으로 실행할 수 있습니다. 두 모듈을 모두 실행하여 메시지 생산과 소비를 테스트할 수 있습니다.

### Producer 모듈 실행
```bash
./gradlew producer:bootRun
```
또는 JAR 파일을 직접 실행:
```bash
java -jar producer/build/libs/producer-0.0.1-SNAPSHOT.jar
```

### Consumer 모듈 실행
```bash
./gradlew consumer:bootRun
```
또는 JAR 파일을 직접 실행:
```bash
java -jar consumer/build/libs/consumer-0.0.1-SNAPSHOT.jar
```

## 모듈 간 통신
Producer 모듈은 REST API를 통해 메시지를 받아 Kafka 토픽으로 발행합니다. Consumer 모듈은 동일한 Kafka 토픽을 구독하여 메시지를 처리합니다.

기본 설정:
- Producer는 8070 포트에서 실행됩니다.
- Consumer는 8090 포트에서 실행됩니다.
- 두 모듈은 기본적으로 로컬 Kafka 서버(localhost:9092)에 연결됩니다.

## 설정 커스터마이징
각 모듈의 `application.yml` 파일을 수정하여 포트, Kafka 서버 주소 등의 설정을 변경할 수 있습니다.

### Kafka 설정 예시
Producer 모듈의 `application.yml`:
```yaml
spring:
  kafka:
    producer:
      bootstrap-servers: localhost:9092
      key-serializer: org.apache.kafka.common.serialization.StringSerializer
      value-serializer: org.apache.kafka.common.serialization.StringSerializer
```

Consumer 모듈의 `application.yml`:
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

## 각 모듈 상세 정보
각 모듈에 대한 자세한 정보는 해당 모듈의 README 파일을 참조하세요:
- [Producer 모듈 README](producer/README.md)
- [Consumer 모듈 README](consumer/README.md)

## Docker Compose

### Docker Compose란?
Docker Compose는 다중 컨테이너 Docker 애플리케이션을 정의하고 실행하기 위한 도구입니다. YAML 파일을 사용하여 애플리케이션의 서비스를 구성하고, 단일 명령으로 모든 서비스를 생성하고 시작할 수 있습니다.

### Docker Compose 설치
Docker Compose를 사용하기 위해서는 Docker가 먼저 설치되어 있어야 합니다.
- Docker Desktop을 설치하면 Docker Compose가 함께 설치됩니다.
- Linux에서는 별도로 Docker Compose를 설치해야 할 수 있습니다.

### Docker Compose 실행 방법
프로젝트 루트 디렉토리에서 다음 명령어를 실행하여 Kafka 서버를 시작할 수 있습니다:

```bash
docker-compose up -d
```

서비스를 중지하려면 다음 명령어를 사용합니다:

```bash
docker-compose down
```

로그를 확인하려면:

```bash
docker-compose logs -f
```

### Kafka 설정 상세 정보
이 프로젝트의 `docker-compose.yml` 파일은 다음과 같은 특징을 가진 Kafka 서버를 설정합니다:

1. **KRaft 모드**: Zookeeper 없이 Kafka를 실행합니다.
2. **Confluent Platform 7.4.1**: Kafka 3.4.1을 포함하는 Confluent Platform을 사용합니다.
3. **포트 설정**:
   - 9092: 내부 통신용 포트
   - 29092: 외부 클라이언트 연결용 포트
4. **데이터 영속성**: 볼륨을 사용하여 Kafka 데이터를 영속적으로 저장합니다.
5. **헬스체크**: Kafka 서버가 정상적으로 시작되었는지 확인하기 위한 헬스체크가 구성되어 있습니다.

### 특별한 설정
1. **KRaft 모드 설정**:
   - `KAFKA_PROCESS_ROLES`: broker와 controller 역할을 모두 수행
   - `KAFKA_NODE_ID`: 노드 식별자
   - `KAFKA_CONTROLLER_QUORUM_VOTERS`: 컨트롤러 쿼럼 구성
   - `KAFKA_CONTROLLER_LISTENER_NAMES`: 컨트롤러 통신에 사용할 리스너 이름
   - `CLUSTER_ID`: 클러스터 식별자

2. **리스너 설정**:
   - `KAFKA_LISTENERS`: 내부 및 외부 연결을 위한 리스너 설정
   - `KAFKA_ADVERTISED_LISTENERS`: 클라이언트에게 알려질 리스너 주소

3. **토픽 설정**:
   - `KAFKA_AUTO_CREATE_TOPICS_ENABLE`: 토픽 자동 생성 활성화
   - `KAFKA_OFFSETS_TOPIC_REPLICATION_FACTOR`: 오프셋 토픽 복제 팩터
   - `KAFKA_TRANSACTION_STATE_LOG_MIN_ISR`: 트랜잭션 상태 로그 최소 ISR
   - `KAFKA_TRANSACTION_STATE_LOG_REPLICATION_FACTOR`: 트랜잭션 상태 로그 복제 팩터

### 주의사항
- Docker Compose를 사용하여 Kafka를 실행할 경우, 애플리케이션의 Kafka 연결 설정이 Docker 네트워크에 맞게 구성되어야 합니다.
- 로컬 개발 환경에서는 `localhost:9092`로 연결할 수 있습니다.
- 실제 운영 환경에서는 보안, 성능, 안정성을 고려한 추가 설정이 필요할 수 있습니다.
- 애플리케이션을 실행하기 전에 Kafka 서버가 실행 중이어야 합니다.
- 토픽이 존재하지 않는 경우 자동으로 생성되도록 설정할 수 있습니다.
