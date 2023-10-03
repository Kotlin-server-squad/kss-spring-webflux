import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    id("org.springframework.boot") version "3.1.4" apply false
    id("io.spring.dependency-management") version "1.1.3" apply false
    kotlin("jvm") version "1.9.0" apply false
    kotlin("plugin.spring") version "1.9.0" apply false
}

group = "cz.kss.proj"
version = "0.0.1-SNAPSHOT"

subprojects {
    repositories {
        mavenCentral()
    }

    apply {
        plugin("org.jetbrains.kotlin.jvm")
        plugin("io.spring.dependency-management")
        plugin("org.jetbrains.kotlin.plugin.spring")
        plugin("org.springframework.boot")
    }

    tasks.withType<KotlinCompile> {
        kotlinOptions {
            freeCompilerArgs += "-Xjsr305=strict"
            jvmTarget = "20"
        }
    }

    tasks.withType<Test> {
        useJUnitPlatform()
    }
}

