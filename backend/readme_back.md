# Backend - Sistema de Gestión de Órdenes

## Tecnologías
- Java 17+
- Spring Boot 3.x
- Spring Web
- JWT (jjwt)
- Bean Validation (Jakarta Validation)
- Maven / Gradle

## Estructura del Proyecto

backend/
├── src/main/java/com/example/ordenes/
│ ├── config/ # Configuraciones (Security)
│ ├── controller/ # Controladores REST
│ │ ├── AuthController.java
│ │ └── OrderController.java
│ ├── dto/ # Data Transfer Objects
│ │ ├── LoginRequest.java
│ │ ├── LoginResponse.java
│ │ └── StatusUpdateRequest.java
│ ├── exception/ # Excepciones personalizadas
│ │ ├── GlobalExceptionHandler.java
│ │ ├── InvalidStatusException.java
│ │ ├── InvalidStatusTransitionException.java
│ │ └── OrderNotFoundException.java
│ ├── model/ # Entidades
│ │ ├── Item.java
│ │ └── Order.java
│ ├── repository/ # Repositorios (en memoria)
│ │ └── OrderRepository.java
│ ├── security/ # Seguridad JWT
│ │ ├── JwtService.java
│ │ └── MockUserService.java
│ └── service/ # Lógica de negocio
│ └── OrderService.java
└── src/main/resources/
└── application.properties



## Requisitos Previos

- Java 17 o superior
- Maven 3.6+ o Gradle 7+
- Puerto 8080 disponible

## Configuración

### application.properties
```properties
server.port=8080
# No se requiere base de datos


### Usuarios de prueba hardcodeados

- admin / admin123
- user / user123

## Levantar el Backend

### Navegar al directorio del backend
cd backend

### Limpiar y compilar
mvn clean install

### Ejecutar la aplicación
mvn spring-boot:run