plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {
    implementation(projects.architectureDomain)
    implementation(libs.kotlinx.coroutines.core)
    testImplementation(libs.test.junit)
}
