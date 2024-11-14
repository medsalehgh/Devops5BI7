# Use the official OpenJDK image from Docker Hub
FROM openjdk:17-jdk-slim as build

# Set the working directory in the container
WORKDIR /app

# Copy the Spring Boot JAR file into the container at /app
COPY target/tp-foyer-5.0.0.jar /app/tp-foyer.jar

# Expose port 8080 for the application
EXPOSE 8087

# Run the application
ENTRYPOINT ["java", "-jar", "/app/tp-foyer.jar"]
