package com.khazena.khazena.core.data.session

import com.khazena.khazena.core.domain.session.SessionRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

class LocalSessionRepository : SessionRepository {
    override val isSignedIn: Flow<Boolean> = MutableStateFlow(false).asStateFlow()
}
