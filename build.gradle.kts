plugins {
    kotlin("jvm") version "2.3.21"
    kotlin("kapt") version "2.3.21"
    kotlin("plugin.jpa") version "2.3.21"
    kotlin("plugin.spring") version "2.3.21"
    kotlin("plugin.serialization") version "2.3.21"
    id("org.flywaydb.flyway") version "11.13.2"
    id("org.springframework.boot") version "4.1.1"
    id("io.spring.dependency-management") version "1.1.7"
    id("it.nicolasfarabegoli.conventional-commits") version "3.1.3"
}

group = "org.scobca"
version = "0.0.1"
description = "scan-shuffle"

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(21)
    }
}

repositories {
    mavenCentral()
}

val mapstructVersion = providers.gradleProperty("mapstructVersion").get()
val jsonwebtokenVersion = providers.gradleProperty("jsonwebtokenVersion").get()
val jaxbApiVersion = providers.gradleProperty("jaxbApiVersion").get()
val kotlinxSerializationVersion = providers.gradleProperty("kotlinxSerializationVersion").get()
val kotlinxCoroutinesVersion = providers.gradleProperty("kotlinxCoroutinesVersion").get()
val dotenvSpringVersion = providers.gradleProperty("dotenvSpringVersion").get()
val javaxAnnotationVersion = providers.gradleProperty("javaxAnnotationVersion").get()

dependencies {
    // Web: MVC controllers and server-rendered HTML templates.
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // Security: admin login and authorization checks in templates.
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

    // JWT support and the JAXB API.
    implementation("io.jsonwebtoken:jjwt:$jsonwebtokenVersion")
    implementation("javax.xml.bind:jaxb-api:$jaxbApiVersion")

    // Data: JPA and Kotlin reflection support.
    implementation("org.springframework.boot:spring-boot-starter-data-jpa")
    implementation("org.jetbrains.kotlin:kotlin-reflect")

    // Compile-time mapping between data models.
    implementation("org.mapstruct:mapstruct:$mapstructVersion")
    kapt("org.mapstruct:mapstruct-processor:$mapstructVersion")

    // PostgreSQL: schema migrations and the JDBC driver at runtime.
    implementation("org.springframework.boot:spring-boot-starter-flyway")
    implementation("org.flywaydb:flyway-database-postgresql")
    runtimeOnly("org.postgresql:postgresql")

    // JSON serialization with support for Kotlin classes.
    implementation("tools.jackson.module:jackson-module-kotlin")

    // Kotlin serialization and coroutines.
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-core:$kotlinxSerializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:$kotlinxSerializationVersion")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:$kotlinxCoroutinesVersion")

    // Utilities: environment variables and the Java annotation API.
    implementation("one.stayfocused.spring:dotenv-spring-boot:$dotenvSpringVersion")
    implementation("javax.annotation:javax.annotation-api:$javaxAnnotationVersion")

    // Automatic restart during development.
    developmentOnly("org.springframework.boot:spring-boot-devtools")

    // Generate metadata for configuration properties.
    annotationProcessor("org.springframework.boot:spring-boot-configuration-processor")

    // Spring test modules and Kotlin integration with JUnit 5.
    testImplementation("org.springframework.boot:spring-boot-starter-data-jpa-test")
    testImplementation("org.springframework.boot:spring-boot-starter-flyway-test")
    testImplementation("org.springframework.boot:spring-boot-starter-security-test")
    testImplementation("org.springframework.boot:spring-boot-starter-thymeleaf-test")
    testImplementation("org.springframework.boot:spring-boot-starter-webmvc-test")
    testImplementation("org.jetbrains.kotlin:kotlin-test-junit5")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

flyway {
    url = ""
    user = ""
    password = ""
    locations = arrayOf("classpath:db/migration")
    schemas = arrayOf("public")
}

kotlin {
    compilerOptions {
        freeCompilerArgs.addAll("-Xjsr305=strict", "-Xannotation-default-target=param-property")
    }
}

allOpen {
    annotation("jakarta.persistence.Entity")
    annotation("jakarta.persistence.MappedSuperclass")
    annotation("jakarta.persistence.Embeddable")
}

tasks.withType<Test> {
    val excludedTestTags = providers.gradleProperty("excludeTestTags").orNull

    useJUnitPlatform {
        excludedTestTags
            ?.split(",")
            ?.map(String::trim)
            ?.filter(String::isNotBlank)
            ?.let { excludeTags(*it.toTypedArray()) }
    }

    jvmArgs("-XX:+EnableDynamicAgentLoading")

    filter {
        isFailOnNoMatchingTests = false
    }
}

conventionalCommits {
    warningIfNoGitRoot = true
    types += listOf("build", "chore", "docs", "feat", "fix", "refactor", "style", "test")
    scopes = emptyList()
    successMessage = "Сообщение коммита соответствует стандартам Conventional Commit."
    failureMessage = "Сообщение коммита не соответствует стандартам Conventional Commit."
}
