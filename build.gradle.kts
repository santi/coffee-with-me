import org.gradle.api.tasks.testing.logging.TestExceptionFormat
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

buildscript {
    repositories {
        mavenCentral()
    }
}

plugins {
    id("org.springframework.boot") version "2.2.6.RELEASE" apply false
    id("io.spring.dependency-management") version "1.0.9.RELEASE" apply false
    id("org.jetbrains.kotlin.plugin.jpa") version "1.3.71" apply false
    kotlin("jvm") version "1.3.71" apply false
    kotlin("plugin.spring") version "1.3.71" apply false
}

allprojects {
    group = "chat.letscoffee"
    version = "0.0.1-SNAPSHOT"

    tasks.withType<JavaCompile> {
        sourceCompatibility = "1.8"
        targetCompatibility = "1.8"
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs = listOf("-Xjsr305=strict")
            jvmTarget = "1.8"
        }
    }

}

subprojects {
    repositories {
        mavenCentral()
    }

    apply {
        plugin("io.spring.dependency-management")
    }

    tasks.withType<Test> {
        systemProperty("spring.profiles.active", "test")
        testLogging {
            exceptionFormat = TestExceptionFormat.FULL
        }
        jvmArgs = listOf(
            // Mutes the 'Illegal reflective access' warnings from groovy,
            // remove after https://issues.apache.org/jira/browse/GROOVY-8339 is fixed
            "java.base/java.io",
            "java.base/java.lang",
            "java.base/java.lang.invoke",
            "java.base/java.lang.reflect",
            "java.base/java.math",
            "java.base/java.net",
            "java.base/java.nio.charset",
            "java.base/java.security",
            "java.base/java.time",
            "java.base/java.time.chrono",
            "java.base/java.time.format",
            "java.base/java.time.temporal",
            "java.base/java.util",
            "java.base/java.util.concurrent",
            "java.base/java.util.concurrent.atomic",
            "java.base/java.util.function",
            "java.base/java.util.stream",
            "java.sql/java.sql"
        ).map {
            "--add-opens=${it}=ALL-UNNAMED"
        }.plus("--illegal-access=deny")
    }

}
