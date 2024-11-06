FROM openjdk:latest AS builder

WORKDIR /app

COPY . .

RUN tr -d '\r' < mvnw > mvnw.lf && \
    mv mvnw.lf mvnw && \
    chmod +x mvnw && \
    ./mvnw clean install


FROM openjdk:latest

WORKDIR /app

COPY --from=builder /app/target/tp-foyer-5.0.0.jar /app/target/tp-foyer-5.0.0.jar
RUN chmod +x /app/target/tp-foyer-5.0.0.jar

EXPOSE 8080

CMD ["java", "-jar", "tp-foyer-5.0.0.jar"]

