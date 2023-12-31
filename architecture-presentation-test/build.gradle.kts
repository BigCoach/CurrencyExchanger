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

    implementation(libs.kotlinx.coroutines.core)

    implementation(libs.test.junit)
    implementation(libs.test.mockito.kotlin)
    implementation(libs.test.kotlinx.coroutines)
    implementation(projects.coroutineTest)

}
