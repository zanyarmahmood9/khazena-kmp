import com.khazena.convention.lib
import com.khazena.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class KmpDataConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.khazena.convention.kmp.library")
            apply("org.jetbrains.kotlin.plugin.serialization")
        }

        dependencies {
            "commonMainImplementation"(project(":core:domain"))
            if (path != ":core:data") "commonMainImplementation"(project(":core:data"))

            "commonMainImplementation"(libs.lib("supabase-postgrest"))
            "commonMainImplementation"(libs.lib("kotlinx-serialization-json"))
            "commonMainImplementation"(libs.lib("koin-core"))
        }
    }
}
