package com.khazena.khazena.core.presentation.state

import androidx.compose.runtime.Immutable
import com.khazena.khazena.core.domain.DataError
import com.khazena.khazena.core.domain.Result
import com.khazena.khazena.core.presentation.UiText
import com.khazena.khazena.core.presentation.toUiText

@Immutable
sealed interface LoadState<out T> {
    data object Loading : LoadState<Nothing>
    data object Empty : LoadState<Nothing>
    data class Error(val message: UiText) : LoadState<Nothing>
    data class Data<T>(val value: T) : LoadState<T>
}

val LoadState<*>.hasData: Boolean get() = this is LoadState.Data

fun <T> Result<T, DataError>.toLoadState(isEmpty: (T) -> Boolean): LoadState<T> = when (this) {
    is Result.Success -> if (isEmpty(data)) LoadState.Empty else LoadState.Data(data)
    is Result.Error -> LoadState.Error(error.toUiText())
}
