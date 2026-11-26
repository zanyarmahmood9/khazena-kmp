plugins {
    alias(libs.plugins.convention.cmp.feature)
}

kotlin {
    sourceSets {
        commonMain.dependencies {
            implementation(projects.feature.store.domain)
        }
    }
}
