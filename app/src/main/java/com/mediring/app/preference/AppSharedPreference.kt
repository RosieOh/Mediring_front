package com.mediring.app.preference

import android.content.Context
import android.content.SharedPreferences

class AppSharedPreference(context: Context) {
    companion object {
        const val PREFS_FILE_NAME = "prefs"

        const val PREFS_KEY_USER_ID = "user_id"
        const val PREFS_KEY_AUTH_TOKEN = "auth_token"
        const val PREFS_KEY_TOKEN_TYPE = "token_type"
    }

    private val pref: SharedPreferences = context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

    var userId: Int
        get() = pref.getInt(PREFS_KEY_USER_ID, 0)
        set(value) = pref.edit().putInt(PREFS_KEY_USER_ID, value).apply()

    var accessToken: String
        get() = pref.getString(PREFS_KEY_AUTH_TOKEN, "") ?: run { "" }
        set(value) = pref.edit().putString(PREFS_KEY_AUTH_TOKEN, value).apply()

    var tokenType: String
        get() = pref.getString(PREFS_KEY_TOKEN_TYPE, "Bearer") ?: run { "" }
        set(value) = pref.edit().putString(PREFS_KEY_TOKEN_TYPE, value).apply()
}