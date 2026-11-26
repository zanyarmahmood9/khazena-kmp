import com.khazena.convention.configureKotlinMultiplatform
import com.khazena.convention.lib
import com.khazena.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/** Pure KMP module (Android + iOS), no Compose. Use for `domain` and `data` modules. */
class KmpLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("org.jetbrains.kotlin.multiplatform")
            apply("com.android.kotlin.multiplatform.library")
        }

        configureKotlinMultiplatform()

        dependencies {
            "commonTestImplementation"(libs.lib("kotlin-test"))
        }
    }
}
