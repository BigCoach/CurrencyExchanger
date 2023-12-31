plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

dependencies {
    implementation(projects.coroutine)

    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.test.kotlinx.coroutines)
}
