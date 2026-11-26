package com.khazena.convention

import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import org.gradle.api.Project
import org.gradle.api.plugins.BasePluginExtension
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun Project.configureKotlinMultiplatform() {
    // Two modules both named "data" would otherwise produce clashing AAR / klib names.
    extensions.configure<BasePluginExtension> {
        archivesName.set(pathToModuleName())
    }

    extensions.configure<KotlinMultiplatformExtension> {
        targets.withType<KotlinMultiplatformAndroidLibraryTarget>().configureEach {
            namespace = pathToPackageName()
            compileSdk = libs.int("android-compileSdk")
            minSdk = libs.int("android-minSdk")
            compilerOptions.jvmTarget.set(jvmTarget)
            withHostTest { isIncludeAndroidResources = true }
        }

        iosArm64()
        iosSimulatorArm64()

        compilerOptions {
            freeCompilerArgs.add("-Xexpect-actual-classes")
        }
    }
}
