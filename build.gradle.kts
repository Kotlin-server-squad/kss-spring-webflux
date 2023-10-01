import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "3.1.4"
	id("io.spring.dependency-management") version "1.1.3"
	id("org.jetbrains.kotlin.plugin.allopen") version "1.9.0"
	kotlin("jvm") version "1.9.0"
	kotlin("plugin.spring") version "1.9.0"
}


allprojects {
	repositories {
		mavenCentral()
	}
}

subprojects {
	group = "cz.kss.proj"
	version = "0.0.1-SNAPSHOT"

	apply(plugin = "org.springframework.boot")
	apply(plugin = "io.spring.dependency-management")
	apply(plugin = "org.jetbrains.kotlin.plugin.allopen")
	apply(plugin = "jvm")
	apply(plugin = "plugin.spring")

	dependencies {
		implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
		implementation("org.springframework.boot:spring-boot-starter-webflux")
		implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
		implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
		implementation("org.jetbrains.kotlin:kotlin-reflect")
		implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
		runtimeOnly("org.postgresql:postgresql")
		runtimeOnly("org.postgresql:r2dbc-postgresql")
		testImplementation("org.springframework.boot:spring-boot-starter-test")
		testImplementation("io.projectreactor:reactor-test")
		testImplementation("io.kotest:kotest-runner-junit5-jvm:5.7.2")
		testImplementation("io.kotest:kotest-assertions-core-jvm:5.7.2")
		testImplementation("io.mockk:mockk:1.13.8")
	}

	java {
		sourceCompatibility = JavaVersion.VERSION_20
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


