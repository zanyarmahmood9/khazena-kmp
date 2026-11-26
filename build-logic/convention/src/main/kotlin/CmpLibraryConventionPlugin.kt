import com.android.build.api.dsl.KotlinMultiplatformAndroidLibraryTarget
import com.khazena.convention.lib
import com.khazena.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

/** KMP module with Compose Multiplatform UI. Use for design-system / shared UI modules. */
class CmpLibraryConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        with(pluginManager) {
            apply("com.khazena.convention.kmp.library")
            apply("org.jetbrains.compose")
            apply("org.jetbrains.kotlin.plugin.compose")
        }

        extensions.configure<KotlinMultiplatformExtension> {
            targets.withType<KotlinMultiplatformAndroidLibraryTarget>().configureEach {
                // Compose resources (composeResources/) need Android resources on.
                androidResources { enable = true }
            }
        }

        dependencies {
            "commonMainImplementation"(libs.lib("compose-runtime"))
            "commonMainImplementation"(libs.lib("compose-foundation"))
            "commonMainImplementation"(libs.lib("compose-material3"))
            "commonMainImplementation"(libs.lib("compose-ui"))
            "commonMainImplementation"(libs.lib("compose-components-resources"))
            "commonMainImplementation"(libs.lib("compose-uiToolingPreview"))
            "androidMainImplementation"(libs.lib("compose-uiTooling"))
        }
    }
}
