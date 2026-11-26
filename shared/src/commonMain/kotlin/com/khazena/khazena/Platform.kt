package com.khazena.khazena

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform