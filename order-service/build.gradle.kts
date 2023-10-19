val kotest_version: String by extra
val kotlinx_version: String by extra
val kotlinx_reactor_version: String by extra
val mockk_version: String by extra
val reactor_test_version: String by extra

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")

    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:$kotlinx_reactor_version")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinx_version")
    runtimeOnly("org.jetbrains.kotlinx:kotlinx-coroutines-slf4j:$kotlinx_version")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")
    implementation("org.flywaydb:flyway-core")
    implementation("org.springframework:spring-jdbc")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotest_version")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotest_version")
    testImplementation("io.mockk:mockk:$mockk_version")
    testImplementation("io.projectreactor:reactor-test:$reactor_test_version")
    testImplementation("com.h2database:h2")
    testImplementation("io.r2dbc:r2dbc-h2:1.0.0.RELEASE")
}
