# Gestión de Equipos de Fútbol - API REST

Este proyecto es una API REST desarrollada con Spring Boot 3 y Java 17 que permite gestionar información sobre equipos de fútbol. 
La API ofrece funcionalidades para consultar, crear, actualizar y eliminar equipos, así como buscar equipos por nombre.
<br>Este proyecto forma parte de la prueba tecnica para desarrollador backend en Duxsoftware. Para mas informacion [ver enunciado](Enunciado.pdf)

## Características

- **Consultas**:
    - Obtener todos los equipos registrados.
    - Obtener un equipo por su ID.
    - Buscar equipos por nombre.
- **Operaciones CRUD**:
    - Crear un nuevo equipo.
    - Actualizar la información de un equipo existente.
    - Eliminar un equipo.
- **Seguridad**:
    - Autenticación mediante JWT.
    - Protección de endpoints con autenticación.
- **Manejo de excepciones**: Respuestas personalizadas para errores comunes.
- **Documentación**: Generada automáticamente con Swagger.
- **Dockerización**: Implementación lista para ser desplegada en un contenedor Docker.

## Requisitos

- **Java 17**
- **Spring Boot 3**
- **Maven o Gradle** (Gradle se utiliza en este proyecto)
- **Docker** (opcional, para la dockerización)

## Configuración

### Variables de Entorno

Las siguientes variables de entorno se pueden configurar en `application.properties` o se pueden pasar en tiempo de ejecución al contenedor Docker:

- `SERVER_PORT`: Puerto en el que se ejecutará la aplicación.
- `JWT_SECRET`: Secreto que se utiliza para firmar y verificar el JWT
- `JWT_EXP_TIME`: Tiempo que tarda el JWT en expirar
- `DATABASE_URL`: URL de la base de datos.
- `DB_USERNAME`: Nombre de usuario de la base de datos.
- `DB_PASSWORD`: Contraseña de la base de datos.
- `SPRING_SECURITY_USER`: Usuario predeterminado de spring security
- `SPRING_SECURITY_PASSWORD`: Contraseña predeterminada de spring security

De todas maneras, para fines practicos, cada variable tiene establecido un valor por defecto.

### Archivo `.env`

Puedes crear un archivo `.env` en la raíz del proyecto para definir estas variables, en caso contrario sus valores por defecto seran:

```dotenv
SERVER_PORT=8080

JWT_SECRET=!sSq^ox^oz2!yu!ZhBqA2Uy6vmwzr!8nVCVg&LEpRVYc@8xfFraf9&utQETbrnpd
JWT_EXP_TIME=300000

SPRING_SECURITY_USER=user
SPRING_SECURITY_PASSWORD=password

DATABASE_URL=jdbc:h2:mem:dux
DATABASE_USER=sa
DATABASE_PASSWORD=password
```

## Ejecución del Proyecto

### 1. Compilar y Ejecutar Localmente

Para compilar y ejecutar el proyecto localmente usando Gradle:
```bash
./gradlew bootRun
```

### 2. Dockerizacion
El proyecto incluye un `Dockerfile` para construir una imagen Docker.

#### Para construir la imagen:
```bash
docker build -t nombre-de-la-imagen .
```

#### Para ejecutar el contenedor:
```bash
docker run --env-file .env nombre-de-la-imagen
```
En caso de no haber configurado el `.env` no enviar `--env-file .env`

## Endpoints principales
- `GET /equipos`: Consulta todos los equipos.
- `GET /equipos/{id}`: Consulta un equipo por su ID.
- `GET /equipos/buscar?nombre={valor}`: Busca equipos por nombre.
- `POST /equipos`: Crea un nuevo equipo.
- `PUT /equipos/{id}`: Actualiza la información de un equipo.
- `DELETE /equipos/{id}`: Elimina un equipo.
- `POST /auth/login`: Autenticación mediante JWT.

## Pruebas
Las pruebas unitarias están implementadas utilizando JUnit 5 y Mockito. Para ejecutar las pruebas:
```bash
./gradlew test
```

## Documentación
La documentación de la API está disponible en `http://localhost:8080/swagger-ui.html` una vez que la aplicación esté en ejecución.

## Manejo de excepciones
El proyecto cuenta con un `GlobalExceptionHandler` para manejar de manera centralizada las excepciones que ocurran durante la ejecución de la API.