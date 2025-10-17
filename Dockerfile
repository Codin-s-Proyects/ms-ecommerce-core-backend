# Dockerfile (Java 21, Gradle build + minimal runtime image)
FROM eclipse-temurin:21-jdk as build

WORKDIR /app
# copia gradle wrapper & configs first for cache layer
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./
# si usas build.gradle (groovy) ajusta nombres
RUN chmod +x ./gradlew

# copia el resto del código
COPY . .

# construye el jar (usa bootJar)
RUN ./gradlew clean bootJar --no-daemon

# runtime image: pequeña y segura
FROM eclipse-temurin:21-jre
WORKDIR /app

# copia jar del stage build
COPY --from=build /app/build/libs/*.jar app.jar

# puerto expuesto (Spring Boot default)
EXPOSE 8080

# comando de inicio
ENTRYPOINT ["java","-jar","/app/app.jar"]
