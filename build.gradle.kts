plugins {
    java
    id("org.springframework.boot") version "3.5.6"
    id("io.spring.dependency-management") version "1.1.7"
    id("org.flywaydb.flyway") version "11.13.2"
}

group = "codin"
version = "0.0.1-SNAPSHOT"
description = "ms-backend-core"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

configurations {
    compileOnly {
        extendsFrom(configurations.annotationProcessor.get())
    }
}

repositories {
    mavenCentral()
}

flyway {
    url = System.getenv("FLYWAY_URL")
    user = System.getenv("FLYWAY_USER")
    password = System.getenv("FLYWAY_PASSWORD")
    schemas = arrayOf("public")
    locations = arrayOf("classpath:db/migration")
    baselineOnMigrate = true
}

dependencies {
    // --- Spring Boot starters ---
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")

    // --- Database ---
    runtimeOnly("org.postgresql:postgresql")

    // Flyway
    implementation("org.flywaydb:flyway-core")
    implementation("org.flywaydb:flyway-database-postgresql")

    // --- JWT (jjwt) ---
    implementation("io.jsonwebtoken:jjwt:0.12.3")

    // --- Security crypto (Argon2) ---
    implementation("org.springframework.security:spring-security-crypto")
    implementation("org.bouncycastle:bcprov-jdk18on:1.76")

    // --- Lombok ---
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")

    // --- Swagger OpenAPI ---
    implementation("org.springframework:spring-webmvc:6.2.3")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.7.0")

    // --- Test ---
    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("org.springframework.security:spring-security-test")
    testImplementation("org.testcontainers:postgresql") // testcontainers
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")

    // OpenAI
    implementation ("org.springframework.boot:spring-boot-starter-webflux")

    // Resilence
    implementation("io.github.resilience4j:resilience4j-spring-boot3:2.2.0")
    implementation("io.github.resilience4j:resilience4j-annotations:2.2.0")
    implementation("io.github.resilience4j:resilience4j-retry:2.2.0")
    implementation("io.github.resilience4j:resilience4j-circuitbreaker:2.2.0")
    implementation("io.github.resilience4j:resilience4j-timelimiter:2.2.0")

}

tasks.withType<Test> {
    useJUnitPlatform()
}
