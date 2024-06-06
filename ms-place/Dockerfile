# Usa una imagen base con Java y Alpine Linux
FROM adoptopenjdk/openjdk11:alpine

# Define las variables de entorno
ENV SPRING_PROFILES_ACTIVE=production
ENV SPRING_APPLICATION_NAME=ms-place
ENV SERVER_PORT=8084
ENV SPRING_DATASOURCE_URL=jdbc:mysql://ticketreck-db-mysql.cf4gakmqo0vw.us-east-1.rds.amazonaws.com:3306/ticketreckDB
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=xWwbbN9sYkKlLJJb6jz5
ENV SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver
ENV SPRING_JPA_HIBERNATE_DDL-AUTO=update
ENV SPRING_JPA_SHOW-SQL=true
ENV SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER-URI=http://localhost:8080/realms/ticketrek
ENV EUREKA_INSTANCE_HOSTNAME=localhost
ENV EUREKA_INSTANCE_INSTANCE-ID=${SPRING_APPLICATION_NAME}:${SPRING_APPLICATION_INSTANCE_ID:${random.value}}
ENV EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka

# Expone el puerto 8084
EXPOSE 8084

# Copia el archivo JAR ejecutable desde el directorio target en la imagen
COPY target/ms-place.jar /app/ms-place.jar

# Comando para ejecutar la aplicación Spring Boot cuando se inicie el contenedor
CMD ["java", "-jar", "/app/ms-place.jar"]
