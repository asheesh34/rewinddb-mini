# ---- Build stage ----
FROM maven:3.9-eclipse-temurin-17 AS build
WORKDIR /app

COPY pom.xml .
RUN mvn -q -B dependency:go-offline

COPY src src
RUN mvn -q -B clean package -DskipTests

# ---- Runtime stage ----
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

RUN addgroup -S rewinddb && adduser -S rewinddb -G rewinddb
COPY --from=build /app/target/*.jar app.jar
RUN chown rewinddb:rewinddb app.jar
USER rewinddb

EXPOSE 8080
ENTRYPOINT ["java", "-jar", "/app/app.jar"]
