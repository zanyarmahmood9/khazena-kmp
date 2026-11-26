import com.khazena.convention.lib
import com.khazena.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

/** `feature:<name>:presentation` modules: Compose UI + ViewModel. Add Koin / navigation here once they're in the catalog. */
class CmpFeatureConventionPlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        pluginManager.apply("com.khazena.convention.cmp.library")

        dependencies {
            "commonMainImplementation"(libs.lib("androidx-lifecycle-viewmodelCompose"))
            "commonMainImplementation"(libs.lib("androidx-lifecycle-runtimeCompose"))
        }
    }
}
