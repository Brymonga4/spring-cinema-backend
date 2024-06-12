# Usa una imagen oficial de Gradle para construir la aplicación
FROM gradle:7.5.1-jdk17 AS build

# Configura el directorio de trabajo
WORKDIR /app

# Copia los archivos del proyecto al contenedor
COPY . .

# Ejecuta el comando de Gradle para construir la aplicación sin ejecutar las pruebas
RUN gradle clean build --no-daemon -x test

# Usa una imagen oficial de JDK para ejecutar la aplicación
FROM openjdk:17-jdk-slim

# Configura el directorio de trabajo
WORKDIR /app

# Copia el archivo JAR desde la imagen de construcción
COPY --from=build /app/build/libs/*.jar app.jar

# Expone el puerto en el que la aplicación se ejecuta
EXPOSE 8080

# Ejecuta la aplicación
ENTRYPOINT ["java", "-jar", "app.jar"]
