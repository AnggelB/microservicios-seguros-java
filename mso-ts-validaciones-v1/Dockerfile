# imagen de Java 17 ligera
FROM eclipse-temurin:17-jdk-alpine

# Copiando el archivo .jar que se acaba de compilar a la raíz del contenedor
COPY target/*.jar app.jar

# indicando a Docker cómo arrancar la aplicación
ENTRYPOINT ["java", "-jar", "/app.jar"]