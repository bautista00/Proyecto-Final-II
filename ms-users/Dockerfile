# Usa una imagen base adecuada para Java
FROM openjdk:11-jre-slim

# Establece el directorio de trabajo dentro del contenedor
WORKDIR /app

# Define las variables de entorno
ENV SPRING_PROFILES_ACTIVE=production
ENV SPRING_APPLICATION_NAME=ms-users
ENV SERVER_PORT=8080
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

# Expone el puerto en el que el microservicio escucha las solicitudes
EXPOSE 8080

# Copia los archivos necesarios al contenedor
COPY target/ms-users.jar /app

# Comando para ejecutar el microservicio cuando el contenedor se inicia
CMD ["java", "-jar", "ms-users.jar"]
