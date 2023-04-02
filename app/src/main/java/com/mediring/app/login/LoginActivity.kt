package com.mediring.app.login

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mediring.app.App
import com.mediring.app.R
import com.mediring.app.main.MainActivity
import com.mediring.app.model.UserEntity
import com.mediring.app.model.request.RequestLogin
import com.mediring.app.model.response.ResponseLogin
import com.mediring.app.service.HttpService
import com.mediring.app.signup.SignupActivity
import kotlinx.android.synthetic.main.activity_login.*
import okhttp3.RequestBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "LoginActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        login_button.setOnClickListener {
            val id = "${login_id_view.text}"
            val pw = "${login_pw_view.text}"
            if (id.isEmpty()) {
                Toast.makeText(this, "아이디를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (pw.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val client = HttpService.getUserService(this)
            client.login(username = id, password = pw, grantType = "password",
                    scope = "write", clientId = "mediring-client", clientSecret = "mediring-secret"
            ).enqueue(object: Callback<ResponseLogin> {
                override fun onFailure(call: Call<ResponseLogin>, t: Throwable) {
                    t.message?.let {
                        Log.e(TAG, it)
                    }
                }

                override fun onResponse(call: Call<ResponseLogin>, response: Response<ResponseLogin>) {
                    if (response.isSuccessful) {
                        response.body()?.let { tokenBody ->
                            val request = RequestLogin(loginId = id, password = pw)
                            client.sessionLogin(request).enqueue(object: Callback<UserEntity> {
                                override fun onFailure(call: Call<UserEntity>, t: Throwable) {
                                    t.message?.let {
                                        Log.e(TAG, it)
                                    }
                                }

                                override fun onResponse(call: Call<UserEntity>, response: Response<UserEntity>) {
                                    if (response.isSuccessful) {
                                        response.body()?.let { body ->
                                            App.user = body
                                            App.accessToken = tokenBody.access_token!!

                                            startActivity(Intent(baseContext, MainActivity::class.java))
                                            finish()
                                        }
                                    }
                                }
                            })
                        }
                    } else {
                        response.errorBody()?.string()?.let {
                            Log.e(TAG, it)
                        }
                        Toast.makeText(baseContext, "로그인에 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }

            })
        }

        signup_button.setOnClickListener {
            startActivity(Intent(this, SignupActivity::class.java))
        }

        kakao_login.setOnClickListener {
            Toast.makeText(this, "준비중입니다.", Toast.LENGTH_SHORT).show()
        }
        naver_login.setOnClickListener {
            Toast.makeText(this, "준비중입니다.", Toast.LENGTH_SHORT).show()
        }
        google_login.setOnClickListener {
            Toast.makeText(this, "준비중입니다.", Toast.LENGTH_SHORT).show()
        }
    }
}