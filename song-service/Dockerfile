# -------- Build Stage --------
FROM eclipse-temurin:17-jdk-alpine AS build

WORKDIR /app

COPY gradlew gradlew.bat settings.gradle ./
COPY gradle ./gradle/

RUN chmod +x ./gradlew

COPY song-service/build.gradle song-service/

RUN ./gradlew :song-service:dependencies --no-daemon

COPY song-service/src song-service/src

RUN ./gradlew :song-service:assemble --no-daemon -x test

# -------- Runtime Stage --------
FROM eclipse-temurin:17-jre-alpine AS runtime

WORKDIR /app

COPY --from=build /app/song-service/build/libs/*.jar app.jar

EXPOSE 8084

CMD ["java", "-jar", "app.jar"]
