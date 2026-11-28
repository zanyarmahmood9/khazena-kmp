package com.khazena.khazena.core.presentation

import com.khazena.khazena.core.domain.DataError
import khazena.core.presentation.generated.resources.Res
import khazena.core.presentation.generated.resources.error_disk_full
import khazena.core.presentation.generated.resources.error_no_internet
import khazena.core.presentation.generated.resources.error_request_timeout
import khazena.core.presentation.generated.resources.error_server
import khazena.core.presentation.generated.resources.error_too_many_requests
import khazena.core.presentation.generated.resources.error_unauthorized
import khazena.core.presentation.generated.resources.error_unknown

fun DataError.toUiText(): UiText = UiText.Resource(
    when (this) {
        DataError.Network.NO_INTERNET -> Res.string.error_no_internet
        DataError.Network.REQUEST_TIMEOUT -> Res.string.error_request_timeout
        DataError.Network.UNAUTHORIZED -> Res.string.error_unauthorized
        DataError.Network.TOO_MANY_REQUESTS -> Res.string.error_too_many_requests
        DataError.Network.SERVER_ERROR, DataError.Network.SERVICE_UNAVAILABLE -> Res.string.error_server
        DataError.Local.DISK_FULL -> Res.string.error_disk_full
        else -> Res.string.error_unknown
    },
)
