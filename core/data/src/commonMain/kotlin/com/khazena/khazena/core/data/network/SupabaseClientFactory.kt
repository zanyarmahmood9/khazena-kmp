package com.khazena.khazena.core.data.network

import com.khazena.khazena.core.data.SupabaseConfig
import io.github.jan.supabase.SupabaseClient
import io.github.jan.supabase.auth.Auth
import io.github.jan.supabase.createSupabaseClient
import io.github.jan.supabase.postgrest.Postgrest
import io.github.jan.supabase.storage.Storage

object SupabaseClientFactory {
    fun create(): SupabaseClient {
        require(SupabaseConfig.URL.isNotBlank() && SupabaseConfig.PUBLISHABLE_KEY.isNotBlank()) {
            "Set SUPABASE_URL and SUPABASE_PUBLISHABLE_KEY in local.properties"
        }
        return createSupabaseClient(SupabaseConfig.URL, SupabaseConfig.PUBLISHABLE_KEY) {
            install(Auth)
            install(Postgrest)
            install(Storage)
        }
    }
}
