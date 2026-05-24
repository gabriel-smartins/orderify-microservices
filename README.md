# Orderify Microservices

Orderify Microservices é uma aplicação de backend construída em Java/Spring Boot para simular o processamento assíncrono de pedidos em um cenário de e-commerce. O sistema resolve o problema de acoplamento entre a API de criação de pedidos e os serviços de processamento e notificação, utilizando RabbitMQ para garantir que cada pedido seja processado e notificado de forma independente.

## Tecnologias e Padrões

- Java 21
- Spring Boot 3.5.x
- Spring AMQP / RabbitMQ
- Spring Web
- Spring Data JPA
- Spring Mail
- SpringDoc OpenAPI
- PostgreSQL
- Docker Compose
- MailHog
- Maven
- Arquitetura de microsserviços
- Mensageria assíncrona / event-driven

## Arquitetura e Decisões de Design

Orderify é composto por três serviços separados, cada um com responsabilidades claras:

1. **orderify-api**
   - Expõe endpoint REST para criação de pedidos.
   - Publica mensagens no RabbitMQ usando um exchange do tipo `fanout`.
   - Fornece documentação OpenAPI/Swagger.

2. **processor**
   - Consome pedidos de uma fila RabbitMQ.
   - Persiste o pedido, itens e produtos em PostgreSQL.
   - Marca o pedido como processado.

3. **notification**
   - Consome pedidos de outra fila RabbitMQ.
   - Envia e-mail de confirmação para o cliente.
   - Usa MailHog para testes locais.

### Fluxo de dados

```text
[client] -> POST /api/v1/orders -> [orderify-api]
            orderify-api publica no exchange RabbitMQ
                     |             |
         [processor queue]  [notification queue]
              |                     |
         [processor]         [notification]
           persiste            envia e-mail
```

### Mensageria e tolerância a falhas

- O `orderify-api` publica o pedido no exchange `orders.v1.order-created`.
- Cada serviço tem sua própria fila ligada ao mesmo exchange, permitindo que processamento e notificação ocorram independentemente.
- O serviço de notificação utiliza uma fila de dead-letter e retry configurado via Spring RabbitMQ:
  - `default-requeue-rejected: false`
  - `retry.max-attempts: 3`
  - `retry.initial-interval: 5s`
  - `retry.multiplier: 2`
- O uso de fanout exchange desacopla o produtor dos consumidores, evitando blocos causados por alta concorrência.

## Serviços do projeto

- `orderify-api`: API REST responsável por receber pedidos e enviá-los ao RabbitMQ.
- `processor`: worker que consome pedidos para salvar em banco de dados.
- `notification`: worker que consome pedidos para enviar notificações por e-mail.

## Pré-requisitos

- Java 21
- Maven 3.9+ (ou wrapper Maven fornecido `./mvnw`)
- Docker e Docker Compose
- PostgreSQL e RabbitMQ via Docker Compose

## Configuração local

1. Crie as variáveis de ambiente necessárias:

```bash
export RABBITMQ_DEFAULT_USER=guest
export RABBITMQ_DEFAULT_PASS=guest
export POSTGRES_USER=postgres
export POSTGRES_PASSWORD=postgres
```

2. Inicie a infraestrutura com Docker Compose:

```bash
cd /home/gabriel-mrt/projects/jetbrains-projects/microservice-order
docker compose up -d
```

3. Execute cada módulo em terminais separados:

```bash
cd orderify-api
./mvnw spring-boot:run
```

```bash
cd processor
./mvnw spring-boot:run
```

```bash
cd notification
./mvnw spring-boot:run
```

> O `orderify-api` roda por padrão em `http://localhost:8080`.
> O MailHog fica disponível em `http://localhost:8025`.
> O RabbitMQ Management fica disponível em `http://localhost:15672`.

## Variáveis de ambiente utilizadas

- `RABBITMQ_DEFAULT_USER`
- `RABBITMQ_DEFAULT_PASS`
- `POSTGRES_USER`
- `POSTGRES_PASSWORD`

## Documentação da API

A API apresenta uma rota principal para criação de pedidos:

- `POST /api/v1/orders`

### Payload de exemplo

```json
{
  "customer": "João Silva",
  "notificationEmail": "joao.silva@example.com",
  "totalValue": 299.90,
  "items": [
    {
      "product": {
        "id": "8d3f5f36-1c4e-42dc-98c4-1e8f5d1a3134",
        "name": "Mouse Gamer",
        "price": 149.95
      },
      "quantity": 2
    }
  ]
}
```

### Exemplo de resposta de sucesso

```json
{
  "id": "c0f6e6b8-5e41-4f25-8a0a-90f5dc3c7e38",
  "customer": "João Silva",
  "items": [
    {
      "id": "3f6bc4e8-1d7b-4ead-9d4c-5b8f4f429f8e",
      "product": {
        "id": "8d3f5f36-1c4e-42dc-98c4-1e8f5d1a3134",
        "name": "Mouse Gamer",
        "price": 149.95
      },
      "quantity": 2
    }
  ],
  "totalValue": 299.9,
  "notificationEmail": "joao.silva@example.com",
  "status": "ON_PROCESSING",
  "dateHour": "2026-05-24 16:45:10"
}
```

### Rotas de documentação

- Swagger UI: `http://localhost:8080/api-docs.html`
- OpenAPI JSON: `http://localhost:8080/api-docs`

## Observações importantes

- A aplicação não implementa autenticação de API no projeto atual.
- O serviço de notificação usa MailHog para capturar e-mails no ambiente local.
- O `processor` persiste entidades JPA no PostgreSQL e usa `ddl-auto: update` para simplificar o desenvolvimento.

## Comandos úteis

- Build do módulo API:

```bash
cd orderify-api
./mvnw clean package
```

- Build do módulo Processor:

```bash
cd processor
./mvnw clean package
```

- Build do módulo Notification:

```bash
cd notification
./mvnw clean package
```

## O que este projeto demonstra

- Modelagem de microsserviços com responsabilidades claras
- Processamento assíncrono baseado em eventos
- Separação entre API, processamento e notificação
- Uso de filas RabbitMQ e configuração de dead-letter queue
- Integração com PostgreSQL e envio de e-mail de teste via MailHog
- Documentação de API via SpringDoc OpenAPI
