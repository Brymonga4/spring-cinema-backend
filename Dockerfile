FROM gradle:7.5.1-jdk17 AS build

# Directorio de trabajo
WORKDIR /app

# Copiar los archivos del proyecto al contenedor
COPY . .

# Comando de Gradle para construir la aplicación sin ejecutar las pruebas
RUN gradle clean build --no-daemon -x test

# Usa una imagen oficial de JDK
FROM openjdk:17-jdk-slim

# Configura el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR desde la imagen
COPY --from=build /app/build/libs/*.jar app.jar

# Expone el puerto
EXPOSE 8080

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
