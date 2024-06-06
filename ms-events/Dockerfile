# Use a base image with Java and Alpine Linux
FROM adoptopenjdk/openjdk11:alpine

# Define environment variables
ENV SPRING_PROFILES_ACTIVE=production
ENV SPRING_DATASOURCE_URL=jdbc:mysql://ticketreck-db-mysql.cf4gakmqo0vw.us-east-1.rds.amazonaws.com:3306/ticketreckDB
ENV SPRING_DATASOURCE_USERNAME=admin
ENV SPRING_DATASOURCE_PASSWORD=xWwbbN9sYkKlLJJb6jz5
ENV SPRING_DATASOURCE_DRIVER_CLASS_NAME=com.mysql.cj.jdbc.Driver
ENV SPRING_JPA_HIBERNATE_DDL_AUTO=update
ENV SPRING_JPA_SHOW_SQL=true
ENV SPRING_SECURITY_OAUTH2_RESOURCESERVER_JWT_ISSUER_URI=http://localhost:8080/realms/ticketrek

# Expose port 8083
EXPOSE 8083

# Copy the executable JAR file from the target directory into the image
COPY target/ms-events.jar /app/ms-events.jar

# Command to run the Spring Boot application when the container starts
CMD ["java", "-jar", "/app/ms-events.jar"]

