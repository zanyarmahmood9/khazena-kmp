import java.util.Properties

plugins {
    alias(libs.plugins.convention.kmp.data)
}

val localProperties = Properties().apply {
    rootProject.file("local.properties").takeIf { it.exists() }?.inputStream()?.use(::load)
}
fun secret(name: String) = localProperties.getProperty(name) ?: System.getenv(name).orEmpty()

val supabaseConfigDir = layout.buildDirectory.dir("generated/supabaseConfig/kotlin")
val generateSupabaseConfig by tasks.registering {
    val url = secret("SUPABASE_URL")
    val key = secret("SUPABASE_PUBLISHABLE_KEY")
    inputs.property("url", url)
    inputs.property("key", key)
    val outputDir = supabaseConfigDir
    outputs.dir(outputDir)
    doLast {
        val file = outputDir.get().file("com/khazena/khazena/core/data/SupabaseConfig.kt").asFile
        file.parentFile.mkdirs()
        file.writeText(
            """
            |package com.khazena.khazena.core.data
            |
            |internal object SupabaseConfig {
            |    const val URL = "$url"
            |    const val PUBLISHABLE_KEY = "$key"
            |}
            |""".trimMargin(),
        )
    }
}

kotlin {
    sourceSets {
        commonMain {
            kotlin.srcDir(generateSupabaseConfig)
            dependencies {
                implementation(libs.supabase.auth)
                implementation(libs.supabase.storage)
            }
        }
        androidMain.dependencies {
            implementation(libs.ktor.client.okhttp)
        }
        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
        }
    }
}
