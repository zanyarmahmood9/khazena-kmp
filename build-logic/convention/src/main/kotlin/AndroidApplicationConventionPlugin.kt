import com.android.build.api.dsl.ApplicationExtension
import com.khazena.convention.int
import com.khazena.convention.javaVersion
import com.khazena.convention.jvmTarget
import com.khazena.convention.lib
import com.khazena.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinJvmCompile

class AndroidApplicationConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.android.application")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        extensions.configure<ApplicationExtension> {
            compileSdk = libs.int("android-compileSdk")

            defaultConfig {
                minSdk = libs.int("android-minSdk")
                targetSdk = libs.int("android-targetSdk")
            }
            compileOptions {
                sourceCompatibility = javaVersion
                targetCompatibility = javaVersion
            }
            buildFeatures {
                compose = true
            }
            packaging {
                resources {
                    excludes += "/META-INF/{AL2.0,LGPL2.1}"
                }
            }
        }

        // AGP 9 built-in Kotlin: no kotlin-android plugin, so set the target on the compile tasks.
        tasks.withType<KotlinJvmCompile>().configureEach {
            compilerOptions.jvmTarget.set(jvmTarget)
        }

        dependencies {
            "implementation"(libs.lib("compose-uiToolingPreview"))
            "implementation"(libs.lib("koin-android"))
            "debugImplementation"(libs.lib("compose-uiTooling"))
        }
    }
}
