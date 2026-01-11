# -------- Build Stage --------
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY gradlew gradlew.bat settings.gradle ./
COPY gradle ./gradle/

RUN chmod +x ./gradlew

COPY resource-service/build.gradle resource-service/

RUN ./gradlew :resource-service:dependencies --no-daemon

COPY resource-service/src resource-service/src

RUN ./gradlew :resource-service:assemble --no-daemon -x test

# -------- Runtime Stage --------
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

COPY --from=build /app/resource-service/build/libs/*.jar app.jar

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
