import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    alias(libs.plugins.kotlin.jvm)
    id("java-gradle-plugin")
    id("maven-publish")
}

// For publishToMavenLocal
group = "com.github.oitchau"
version = "1.0-SNAPSHOT"

publishing {
    repositories {
        mavenLocal()
    }
}

gradlePlugin {
    plugins {
        create("LokalisePlugin") {
            id = "com.github.oitchau.lokalise-plugin"
            implementationClass = "com.oitchau.lokalise.LokalisePlugin"
        }
    }
}

dependencies {
    implementation(gradleApi())

    implementation(libs.commons.io)
    implementation(libs.commons.codec)

    //Network
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.gson)
    implementation(libs.okhttp.logging.interceptor)

    testImplementation(libs.junit)
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}
