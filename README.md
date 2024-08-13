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
- `JWT_EXP_TIME`: Tiempo que tarda el JWT en expirar, expresado en milisegundos. Por defecto son 5 minutos
- `DATABASE_URL`: URL de la base de datos.
- `DB_USERNAME`: Nombre de usuario de la base de datos.
- `DB_PASSWORD`: Contraseña de la base de datos.
- `SPRING_SECURITY_USER`: Usuario predeterminado de spring security
- `SPRING_SECURITY_PASSWORD`: Contraseña predeterminada de spring security

De todas maneras, para fines practicos, cada variable tiene establecido un valor por defecto.

### Archivo `.env`
Para modificar los valores anteriores, se puede crear un archivo `.env` en la raíz del proyecto para definir estas variables, en caso contrario sus valores por defecto seran:

```dotenv
SERVER_PORT=8080

JWT_SECRET=!sSq^ox^oz2!yu!ZhBqA2Uy6vmwzr!8nVCVg&LEpRVYc@8xfFraf9&utQETbrnpd
JWT_EXP_TIME=300000

SPRING_SECURITY_USER=user
SPRING_SECURITY_PASSWORD=password

DATABASE_URL=jdbc:h2:mem:testdb
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

Para facilitar la prueba de la aplicacion, se agrego una [coleccion de postman](Equipos%20de%20futbol.postman_collection.json) previamente configurada.

## Pruebas
Las pruebas unitarias están implementadas utilizando JUnit 5 y Mockito. Para ejecutarlas:
```bash
./gradlew test
```
## Base de Datos
### Descripción

Este proyecto utiliza una base de datos en memoria H2 para almacenar información relacionada con el mismo.

### Consola H2

Para acceder a la consola H2 y ejecutar consultas directamente en la base de datos:

1. **Levantar la aplicación**: Asegúrarse de que la aplicación esté corriendo.
2. **Acceder a la consola**: Abrir un navegador web y dirigirse a `http://localhost:8080/h2-console`.
3. **Conectar a la base de datos**: En el login, ingresar las credenciales
   - **JDBC URL**: jdbc:h2:mem:testdb 
   - **Username**: sa 
   - **Password**: password

Todos estos campos son customizables desde el `.env` o el `application.properties`, estos son los valores por defecto

### Script de Inicialización
La base de datos se inicializa automáticamente al inicio de la aplicación mediante un [script SQL](src/main/resources/data.sql). 
Este script inserta registros iniciales en la tabla team con los equipos de fútbol especificados en el enunciado, asi como tambien un usuario por defecto para poder autenticarse y hacer uso de la aplicacion.

Las credenciales de dicho usuario son:
```json
{
  "username": "test",
  "password": "12345"
}
```
## Documentación
La documentación de la API está disponible en `http://localhost:8080/swagger-ui.html` una vez que la aplicación esté en ejecución.
## Manejo de excepciones
El proyecto cuenta con un `GlobalExceptionHandler` para manejar de manera centralizada las excepciones que ocurran durante la ejecución de la API.