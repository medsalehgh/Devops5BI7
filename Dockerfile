FROM openjdk:8-jdk-alpine
EXPOSE 8089
ADD target/achat-1.0.jar achat-1.0.jar
ENTRYPOINT ["java","-jar","/tp-foyer-5.0.0.jar"]

