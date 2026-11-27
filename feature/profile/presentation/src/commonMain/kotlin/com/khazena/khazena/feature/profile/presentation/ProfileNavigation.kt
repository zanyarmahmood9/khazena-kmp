package com.khazena.khazena.feature.profile.presentation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import kotlinx.serialization.Serializable

@Serializable
data object ProfileRoute

fun NavGraphBuilder.profileGraph(onSignIn: () -> Unit, onOpenGallery: (() -> Unit)?) {
    composable<ProfileRoute> { ProfileRoot(onSignIn, onOpenGallery) }
}
