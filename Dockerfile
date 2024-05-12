FROM openjdk:22-jdk-slim

WORKDIR /app

COPY target/user-api-0.0.1-SNAPSHOT.jar /app/user-api-0.0.1-SNAPSHOT.jar

EXPOSE 8080

CMD ["java", "-jar", "user-api-0.0.1-SNAPSHOT.jar"]
