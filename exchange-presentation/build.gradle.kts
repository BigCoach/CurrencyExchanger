plugins {
    id("java-library")
    id("org.jetbrains.kotlin.jvm")
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}


dependencies {
    implementation(projects.architecturePresentation)
    implementation(projects.architectureDomain)
    implementation(projects.exchangeDomain)
    implementation(libs.kotlinx.coroutines.core)
}
