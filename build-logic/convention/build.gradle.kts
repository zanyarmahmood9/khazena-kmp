import org.jetbrains.kotlin.gradle.dsl.JvmTarget

plugins {
    `kotlin-dsl`
}

group = "com.khazena.convention.buildlogic"

dependencies {
    // compileOnly: the real plugin versions come from the root build's classpath (apply false there).
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.kotlin.gradlePlugin)
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

kotlin {
    compilerOptions {
        jvmTarget = JvmTarget.JVM_17
    }
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplication") {
            id = "com.khazena.convention.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("kmpLibrary") {
            id = "com.khazena.convention.kmp.library"
            implementationClass = "KmpLibraryConventionPlugin"
        }
        register("cmpLibrary") {
            id = "com.khazena.convention.cmp.library"
            implementationClass = "CmpLibraryConventionPlugin"
        }
        register("kmpData") {
            id = "com.khazena.convention.kmp.data"
            implementationClass = "KmpDataConventionPlugin"
        }
        register("cmpFeature") {
            id = "com.khazena.convention.cmp.feature"
            implementationClass = "CmpFeatureConventionPlugin"
        }
    }
}
