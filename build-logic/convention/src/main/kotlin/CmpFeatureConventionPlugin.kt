import com.khazena.convention.lib
import com.khazena.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/** `feature:<name>:presentation` modules: Compose UI + ViewModel + core UI modules, Koin, type-safe navigation, Coil, Compottie (Lottie). */
class CmpFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.khazena.convention.cmp.library")
            apply("org.jetbrains.kotlin.plugin.serialization")
        }

        dependencies {
            "commonMainImplementation"(project(":core:domain"))
            "commonMainImplementation"(project(":core:presentation"))
            "commonMainImplementation"(project(":core:designsystem"))

            "commonMainImplementation"(libs.lib("androidx-lifecycle-viewmodelCompose"))
            "commonMainImplementation"(libs.lib("androidx-lifecycle-runtimeCompose"))
            "commonMainImplementation"(libs.lib("koin-compose"))
            "commonMainImplementation"(libs.lib("koin-compose-viewmodel"))
            "commonMainImplementation"(libs.lib("navigation-compose"))
            "commonMainImplementation"(libs.lib("kotlinx-serialization-json"))
            "commonMainImplementation"(libs.lib("coil-compose"))
            "commonMainImplementation"(libs.lib("coil-network-ktor3"))
            "commonMainImplementation"(libs.lib("compottie"))
            "commonMainImplementation"(libs.lib("kotlinx-datetime"))
        }
    }
}
