package com.khazena.khazena.core.domain.session

import kotlinx.coroutines.flow.Flow

interface SessionRepository {
    val isSignedIn: Flow<Boolean>
}
