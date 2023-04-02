package com.mediring.app

import android.app.Application
import com.mediring.app.model.UserEntity

class App : Application() {
    companion object {
        lateinit var user: UserEntity
        var accessToken: String? = null
//        const val SERVER_IP="192.168.1.205"
        const val SERVER_IP="192.168.10.111"
        const val SERVER_PORT=":8082"
        const val SERVER_URL="http://$SERVER_IP$SERVER_PORT"
    }

    override fun onCreate() {
        super.onCreate()
    }
}