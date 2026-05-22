# Docker Deployment - Food Delivery API

## Prerequisites

- Docker
- Docker Compose

## Quick Start

### 1. Build all services

```bash
cd ../../
mvn clean package -DskipTests
```

### 2. Start all services

```bash
cd infrastructure/docker
docker-compose up -d
```

### 3. Stop all services

```bash
docker-compose down
```

### 4. Stop and remove volumes

```bash
docker-compose down -v
```

## Services

| Service | Port | Description |
|---------|------|-------------|
| config-server | 8090 | Centralized configuration server |
| eureka-server | 8091 | Service discovery |
| gateway-server | 8092 | API Gateway |
| auth-service | 8080 | Authentication & Authorization |
| food-service | 8081 | Food/menu management |
| delivery-service | 8082 | Delivery tracking |
| order-service | 8083 | Order processing |
| restaurant-service | 8084 | Restaurant management |
| postgres | 5432 | PostgreSQL database |
| mongo | 27017 | MongoDB database |
| kafka | 9092 | Message broker |
| zookeeper | 2181 | Kafka coordination |

## Configuration

1. Copy `.env.example` to `.env`:
   ```bash
   cp .env.example .env
   ```

2. Update the environment variables in `.env` with your values.

3. Start services:
   ```bash
   docker-compose up -d
   ```

## Access Points

- **API Gateway**: http://localhost:8092
- **Eureka Dashboard**: http://localhost:8091
- **Config Server**: http://localhost:8090

## Troubleshooting

### View logs

```bash
# All services
docker-compose logs -f

# Specific service
docker-compose logs -f auth-service
```

### Rebuild specific service

```bash
docker-compose up -d --build auth-service
```

### Check service health

```bash
docker-compose ps
```
