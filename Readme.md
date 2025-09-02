# Auth & Notification Microservices

Микросервисное приложение для управления пользователями с JWT-аутентификацией и системой уведомлений.

## Архитектура
- **auth service (8080)**: Управление пользователями, JWT-аутентификация, Flyway-миграции
- **notification service (8081)**: Обработка RabbitMQ-сообщений и отправка email-уведомлений
- **PostgreSQL**: Хранение данных пользователей
- **RabbitMQ**: Обмен сообщениями между сервисами
- **MailHog**: Тестовый SMTP-сервер для просмотра отправленных писем

## Технологии
- **Spring Boot** (Web, Security, Data JPA)
- **Spring Security** с JWT
- **PostgreSQL** (через JPA/Hibernate)
- **Flyway** (миграции и тестовые данные)
- **RabbitMQ** (отправка сообщений)
- **Docker** (auth, notification, rabbit, postgres, mailhog)
- **Maven**

## Ключевые функции
- Регистрация и аутентификация пользователей с использованием JWT-токенов
- CRUD-операции для управления пользователями
- При регистрации/изменении/удалении пользователей с ролью USER автоматическая отправка email-уведомлений всем ADMIN-ам
- Логирование операций с помощью AOP и Slf4j
- Автоматические миграции базы данных через Flyway

## Быстрый старт через Docker Compose

1. **Клонируйте репозиторий:**
   ```bash
   git clone https://github.com/trufanovnik/KrainetTraineeApp.git
   ```
2. **Соберите проект:**
   ```bash
   mvn clean package -DskipTests
   ```
3. **Соберите и запустите сервисы:**
   ```bash
   docker-compose up --build
   ```
   
### После запуска проверьте работу сервисов:

- **Auth Service**: http://localhost:8080
- **Notification Service**: http://localhost:8081
- **RabbitMQ Management**: http://localhost:15672 (admin/password)
- **MailHog Web Interface**: http://localhost:8025

### Тестовые данные 

| Username | Password | Email        | Role   |
|----------|----------|--------------|--------|
| user1    | password | user1@kr.com | USER   |
| user2    | password | user2@kr.com | USER   |
| user3    | password | user3@kr.com | USER   |
| user4    | password | user4@kr.com | USER   |
| admin1   | admin    | admin1@kr.com| ADMIN  |
| admin2   | admin    | admin2@kr.com| ADMIN  |


### Контакты
Труфанов Никита

trufanovwork@gmail.com

---