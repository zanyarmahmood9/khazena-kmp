rootProject.name = "Khazena"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            mavenContent {
                includeGroupAndSubgroups("androidx")
                includeGroupAndSubgroups("com.android")
                includeGroupAndSubgroups("com.google")
            }
        }
        mavenCentral()
    }
}

include(":androidApp")
include(":shared")

include(":core:domain")
include(":core:data")
include(":core:presentation")
include(":core:designsystem")
include(":feature:onboarding:presentation")
include(":feature:chooseside:presentation")
include(":feature:home:domain")
include(":feature:home:data")
include(":feature:home:presentation")
include(":feature:store:domain")
include(":feature:store:data")
include(":feature:store:presentation")
include(":feature:review:domain")
include(":feature:review:data")
include(":feature:review:presentation")
include(":feature:notifications:domain")
include(":feature:notifications:data")
include(":feature:notifications:presentation")
include(":feature:search:domain")
include(":feature:search:data")
include(":feature:search:presentation")
include(":feature:wallet:domain")
include(":feature:wallet:data")
include(":feature:wallet:presentation")
include(":feature:membership:domain")
include(":feature:membership:data")
include(":feature:membership:presentation")
include(":feature:profile:domain")
include(":feature:profile:data")
include(":feature:profile:presentation")
include(":feature:scanme:domain")
include(":feature:scanme:data")
include(":feature:scanme:presentation")
include(":feature:business:home:domain")
include(":feature:business:home:data")
include(":feature:business:home:presentation")
include(":feature:business:scan:domain")
include(":feature:business:scan:data")
include(":feature:business:scan:presentation")
include(":feature:business:services:domain")
include(":feature:business:services:data")
include(":feature:business:services:presentation")
include(":feature:business:reports:domain")
include(":feature:business:reports:data")
include(":feature:business:reports:presentation")
include(":feature:business:profile:domain")
include(":feature:business:profile:data")
include(":feature:business:profile:presentation")
