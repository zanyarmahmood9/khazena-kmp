plugins {
    alias(libs.plugins.convention.cmp.feature)
}

kotlin {
    listOf(iosArm64(), iosSimulatorArm64()).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = "Shared"
            isStatic = true
            binaryOption("bundleId", "com.khazena.khazena.shared")
        }
    }
    android {
        withDeviceTestBuilder {
            sourceSetTreeName = "test"
        }.configure {
            instrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        }
    }
    sourceSets {
        commonMain.dependencies {
            implementation(projects.core.domain)
            implementation(projects.core.data)
            implementation(projects.core.presentation)
            implementation(projects.core.designsystem)
            implementation(projects.feature.onboarding.presentation)
            implementation(projects.feature.chooseside.presentation)
            implementation(projects.feature.home.domain)
            implementation(projects.feature.home.data)
            implementation(projects.feature.home.presentation)
            implementation(projects.feature.store.domain)
            implementation(projects.feature.store.data)
            implementation(projects.feature.store.presentation)
            implementation(projects.feature.review.domain)
            implementation(projects.feature.review.data)
            implementation(projects.feature.review.presentation)
            implementation(projects.feature.notifications.domain)
            implementation(projects.feature.notifications.data)
            implementation(projects.feature.notifications.presentation)
            implementation(projects.feature.search.domain)
            implementation(projects.feature.search.data)
            implementation(projects.feature.search.presentation)
            implementation(projects.feature.wallet.domain)
            implementation(projects.feature.wallet.data)
            implementation(projects.feature.wallet.presentation)
            implementation(projects.feature.membership.domain)
            implementation(projects.feature.membership.data)
            implementation(projects.feature.membership.presentation)
            implementation(projects.feature.profile.domain)
            implementation(projects.feature.profile.data)
            implementation(projects.feature.profile.presentation)
            implementation(projects.feature.scanme.domain)
            implementation(projects.feature.scanme.data)
            implementation(projects.feature.scanme.presentation)
            implementation(projects.feature.business.home.domain)
            implementation(projects.feature.business.home.data)
            implementation(projects.feature.business.home.presentation)
            implementation(projects.feature.business.scan.domain)
            implementation(projects.feature.business.scan.data)
            implementation(projects.feature.business.scan.presentation)
            implementation(projects.feature.business.services.domain)
            implementation(projects.feature.business.services.data)
            implementation(projects.feature.business.services.presentation)
            implementation(projects.feature.business.reports.domain)
            implementation(projects.feature.business.reports.data)
            implementation(projects.feature.business.reports.presentation)
            implementation(projects.feature.business.profile.domain)
            implementation(projects.feature.business.profile.data)
            implementation(projects.feature.business.profile.presentation)
        }
        getByName("androidDeviceTest").dependencies {
            implementation(libs.androidx.testRunner)
        }
    }
}
