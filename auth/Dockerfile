FROM eclipse-temurin:21-jre
WORKDIR /app
COPY target/*.jar app.jar
COPY src/main/resources/db/migration /app/migration
ENTRYPOINT ["java", "-jar", "app.jar"]