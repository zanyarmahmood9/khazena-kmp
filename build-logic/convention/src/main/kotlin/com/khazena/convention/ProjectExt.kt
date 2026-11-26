package com.khazena.convention

import org.gradle.api.JavaVersion
import org.gradle.api.Project
import org.gradle.api.artifacts.VersionCatalog
import org.gradle.api.artifacts.VersionCatalogsExtension
import org.gradle.kotlin.dsl.getByType
import org.jetbrains.kotlin.gradle.dsl.JvmTarget

val Project.libs: VersionCatalog
    get() = extensions.getByType<VersionCatalogsExtension>().named("libs")

internal fun VersionCatalog.lib(alias: String) = findLibrary(alias).get()

internal fun VersionCatalog.int(alias: String) = findVersion(alias).get().requiredVersion.toInt()

internal val javaVersion = JavaVersion.VERSION_17
internal val jvmTarget = JvmTarget.JVM_17

/** `:feature:auth:data` -> `com.khazena.khazena.feature.auth.data` */
fun Project.pathToPackageName(): String =
    "com.khazena.khazena" + path.replace(':', '.').replace('-', '_').lowercase()

/** `:feature:auth:data` -> `feature_auth_data` */
fun Project.pathToModuleName(): String = path.replace(':', '_').lowercase().trim('_')

