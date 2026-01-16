# -------- Build Stage --------
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY gradlew gradlew.bat settings.gradle ./
COPY gradle ./gradle/

RUN chmod +x ./gradlew

COPY discovery-service/build.gradle discovery-service/

RUN ./gradlew :discovery-service:dependencies --no-daemon

COPY discovery-service/src discovery-service/src

RUN ./gradlew :discovery-service:assemble --no-daemon -x test

# -------- Runtime Stage --------
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

COPY --from=build /app/discovery-service/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
