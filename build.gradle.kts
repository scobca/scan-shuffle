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
val kotlinxSerializationVersion = providers.gradleProperty("kotlinxSerializationVersion").get()
val kotlinxCoroutinesVersion = providers.gradleProperty("kotlinxCoroutinesVersion").get()
val dotenvSpringVersion = providers.gradleProperty("dotenvSpringVersion").get()
val javaxAnnotationVersion = providers.gradleProperty("javaxAnnotationVersion").get()
val uuidCreatorVersion = providers.gradleProperty("uuidCreatorVersion").get()

dependencies {
    implementation("ru.career-seekers:core:0.1.1")

    // Web: MVC controllers and server-rendered HTML templates.
    implementation("org.springframework.boot:spring-boot-starter-webmvc")
    implementation("org.springframework.boot:spring-boot-starter-thymeleaf")

    // Security: HTTP authentication and JWT bearer token validation.
    implementation("org.springframework.boot:spring-boot-starter-security")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.thymeleaf.extras:thymeleaf-extras-springsecurity6")

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

    // Utilities: environment variables, annotations, and UUID generation.
    implementation("org.springframework.boot:spring-boot-starter-validation")
    implementation("one.stayfocused.spring:dotenv-spring-boot:$dotenvSpringVersion")
    implementation("javax.annotation:javax.annotation-api:$javaxAnnotationVersion")
    implementation("com.github.f4b6a3:uuid-creator:$uuidCreatorVersion")

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
