plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "0.5.0"
}
rootProject.name = "spring-webflux-coroutines"

include(":customer-service")
include(":delivery-service")
include(":notification-service")
include(":order-service")
include(":data-loader-service")