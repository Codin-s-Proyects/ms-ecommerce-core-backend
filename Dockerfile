# Dockerfile recomendado (usa gradle wrapper, Kotlin DSL)
FROM eclipse-temurin:21-jdk as build
WORKDIR /app

# copiar wrapper y archivos de build primero para cache
COPY gradlew .
COPY gradle gradle
COPY build.gradle.kts settings.gradle.kts ./

RUN chmod +x ./gradlew

# copia el resto del código
COPY . .

# construir jar
RUN ./gradlew clean bootJar --no-daemon

FROM eclipse-temurin:21-jre
WORKDIR /app
COPY --from=build /app/build/libs/*.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]
