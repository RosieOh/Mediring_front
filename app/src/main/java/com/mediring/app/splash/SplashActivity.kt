package com.mediring.app.splash

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.mediring.app.R
import com.mediring.app.login.LoginActivity
import com.mediring.app.main.MainActivity

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        startActivity(Intent(baseContext, LoginActivity::class.java))
        finish()
    }
}