# Documentação do Projeto Login Java

---

## 1. Descrição Geral

Este projeto é uma API REST para gerenciamento de login de usuários, construída em Java com Spring Boot, utilizando MongoDB para armazenamento, Docker para containerização e RabbitMQ para envio de e-mails assíncronos. A documentação das rotas está integrada com Swagger.

---

## 2. Tecnologias Utilizadas

- **Java 17+**
- **Spring Boot**
- **MongoDB** — banco NoSQL para persistência de dados
- **RabbitMQ** — broker de mensagens para envio de e-mails via filas
- **Docker** — para containerização da aplicação e serviços auxiliares
- **Swagger / Springdoc OpenAPI** — documentação automática das APIs
- **SDKMAN!** — gerenciamento das versões de Java, Maven, Gradle (no ambiente dev)

---

## 3. Como rodar o projeto

### Pré-requisitos

- Docker instalado e rodando no seu sistema
- Docker Compose (opcional, recomendado para facilitar)
- (Opcional) SDKMAN! para gerenciar o Java no ambiente local

---

### Rodando com Docker

Você pode rodar a aplicação e seus serviços auxiliares via Docker Compose. Crie um arquivo `docker-compose.yml` no diretório raiz do projeto com o seguinte conteúdo:

```yaml
version: '3.8'

services:
  mongo:
    image: mongo:latest
    container_name: mongo-login
    ports:
      - "27017:27017"
    volumes:
      - mongo-data:/data/db

  rabbitmq:
    image: rabbitmq:3-management
    container_name: rabbitmq-login
    ports:
      - "5672:5672"
      - "15672:15672"

  app:
    build: .
    container_name: login-app
    ports:
      - "8080:8080"
    environment:
      - SPRING_DATA_MONGODB_URI=mongodb://mongo-login:27017/login-db
      - SPRING_RABBITMQ_HOST=rabbitmq-login
      # outras variáveis de ambiente conforme necessário
    depends_on:
      - mongo
      - rabbitmq
    volumes:
      - ./:/app
```

### Comandos úteis

- Subir tudo:

```bash
sudo docker-compose up --build
```

- Parar tudo:

```bash
sudo docker-compose down
```

---

## 4. API Endpoints (via Swagger)

Após subir a aplicação, você pode acessar a documentação interativa via Swagger em:

```
http://localhost:8080/swagger-ui.html
```

### Endpoints principais

| Método | Rota               | Descrição                           | Request Body                      | Response                          |
|--------|--------------------|-----------------------------------|----------------------------------|----------------------------------|
| POST   | /auth/register     | Cadastrar novo usuário             | `UserDto`                        | `RegisterResponseDto`             |
| POST   | /auth/login        | Autenticar usuário (em desenvolvimento) | `AuthenticationDto` (em breve)   | Token JWT e mensagens             |
| POST   | /auth/forgot-password | Solicitar recuperação de senha   | `ForgotPassDto`                  | Mensagem de sucesso ou erro       |
| POST   | /auth/reset-password | Resetar senha                    | `UpdatePasswordDto`              | Mensagem de sucesso ou erro       |

---

## 5. Fluxo de envio de email com RabbitMQ

- Quando um usuário é cadastrado, a aplicação publica uma mensagem na fila RabbitMQ (`queue-email-verify`) contendo os dados para o serviço de email processar e enviar o link de ativação.

- O serviço consumidor da fila RabbitMQ pode ser uma aplicação separada que processa essas mensagens e envia os emails reais.

---

## 6. Estrutura do projeto

- `src/main/java/com/desergm/login/controllers` — controllers REST
- `src/main/java/com/desergm/login/dtos` — objetos de transferência de dados (DTOs)
- `src/main/java/com/desergm/login/models` — modelos do MongoDB
- `src/main/java/com/desergm/login/services` — regras de negócio e serviços (incluindo RabbitMQ)
- `src/main/resources/application.properties` — configuração da aplicação

---

## 7. Rodando local sem Docker

1. Instale Java via SDKMAN!:
```bash
sdk install java
sdk install maven
```

2. Configure seu MongoDB local e RabbitMQ (ou use Docker só para eles)
3. Execute a aplicação via IDE ou:
```bash
mvn spring-boot:run
```

---

## 8. Próximos passos / melhorias

- Implementar autenticação e login via JWT (endpoint de login comentado)
- Criar serviço consumidor da fila RabbitMQ para envio real dos e-mails
- Adicionar testes automatizados (unitários e integração)
- Melhorar tratamento de erros e mensagens de resposta
- Integrar CI/CD com GitHub Actions para build, teste e deploy

---

Se quiser, posso te ajudar a montar um arquivo `README.md` pronto com essa documentação para seu repositório! Quer?
