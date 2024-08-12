# Usa una imagen base de JDK para compilar la aplicación
FROM openjdk:17-jdk-alpine AS build

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo de construcción Gradle/Maven y los archivos fuente
COPY build.gradle settings.gradle gradlew ./
COPY gradle ./gradle
COPY src ./src

# Construye la aplicación
RUN ./gradlew bootJar --no-daemon

# Usa una imagen base de JRE para ejecutar la aplicación
FROM openjdk:17-jdk-alpine

# Establece el directorio de trabajo
WORKDIR /app

# Copia el archivo jar desde la etapa de construcción
COPY --from=build /app/build/libs/*.jar app.jar

# Exponer el puerto en el que se ejecutará la aplicación
EXPOSE 8080

# Comando para ejecutar la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
