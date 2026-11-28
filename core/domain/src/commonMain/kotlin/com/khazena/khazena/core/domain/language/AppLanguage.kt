package com.khazena.khazena.core.domain.language

enum class AppLanguage(val code: String, val isRtl: Boolean) {
    CKB("ckb", isRtl = true),
    AR("ar", isRtl = true),
    EN("en", isRtl = false);

    companion object {
        val Default = CKB

        fun fromCode(code: String?): AppLanguage? = entries.firstOrNull { it.code == code }
    }
}
