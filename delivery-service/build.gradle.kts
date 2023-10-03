val jackson_version: String by extra
val kotest_version: String by extra
val kotlinx_reactor_version: String by extra
val mockk_version: String by extra

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin:$jackson_version")
    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions:$kotlinx_reactor_version")
    implementation("org.jetbrains.kotlin:kotlin-reflect")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor:$kotlinx_reactor_version")

    runtimeOnly("org.postgresql:postgresql")
    runtimeOnly("org.postgresql:r2dbc-postgresql")

    testImplementation("org.springframework.boot:spring-boot-starter-test")
    testImplementation("io.kotest:kotest-runner-junit5-jvm:$kotest_version")
    testImplementation("io.kotest:kotest-assertions-core-jvm:$kotest_version")
    testImplementation("io.mockk:mockk:$mockk_version")
}
