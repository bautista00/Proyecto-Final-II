# Usa una imagen base con Java y Alpine Linux
FROM adoptopenjdk/openjdk11:alpine

# Define las variables de entorno
ENV SPRING_PROFILES_ACTIVE=production
ENV SPRING_APPLICATION_NAME=ms-ticket
ENV SERVER_PORT=8085
ENV SPRING_DATASOURCE_URL=jdbc:mysql://ticketreck-db-mysql.cf4gakmqo0vw.us-east-1.rds.amazonaws.com:3306/ticketreckDB
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=xWwbbN9sYkKlLJJb6jz5
ENV SPRING_DATASOURCE_DRIVER-CLASS-NAME=com.mysql.cj.jdbc.Driver
ENV SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER-URI=http://localhost:8080/realms/ticketrek
ENV EMAIL_USERNAME=ticketrekpi@gmail.com
ENV EMAIL_PASSWORD=xueb lzgg bjmh plxv
ENV EUREKA_INSTANCE_HOSTNAME=localhost
ENV EUREKA_INSTANCE_INSTANCE-ID=${SPRING_APPLICATION_NAME}:${SPRING_APPLICATION_INSTANCE_ID:${random.value}}
ENV EUREKA_CLIENT_SERVICE_URL_DEFAULTZONE=http://localhost:8761/eureka

# Expone el puerto 8085
EXPOSE 8085

# Copia el archivo JAR ejecutable desde el directorio target en la imagen
COPY target/ms-ticket.jar /app/ms-ticket.jar

# Comando para ejecutar la aplicación Spring Boot cuando se inicie el contenedor
CMD ["java", "-jar", "/app/ms-ticket.jar"]
