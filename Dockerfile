# Use OpenJDK 17 as the base image
FROM openjdk:17
# Expose the port that the Spring Boot app will use
EXPOSE 8081
COPY target/tp-foyer-5.0.0.jar tp-foyer-5.0.0.jar
# Run the Spring Boot application
ENTRYPOINT ["java", "-jar", "tp-foyer-5.0.0.jar"]
