package com.mediring.app.signup

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.mediring.app.R
import com.mediring.app.model.UserEntity
import com.mediring.app.model.request.RequestUser
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.activity_signup.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SignupActivity : AppCompatActivity() {
    companion object {
        private const val TAG = "SignupActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        signup_join_button.setOnClickListener {
            val email = "${signup_email_field.text}"
            val name = "${signup_name_filed.text}"
            val password = "${signup_password_filed.text}"
            val phone = "${signup_phone_filed.text}"
            val check = signup_check_box.isChecked

            if (email.isEmpty()) {
                Toast.makeText(this, "이메일을 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (name.isEmpty()) {
                Toast.makeText(this, "이름 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (password.isEmpty()) {
                Toast.makeText(this, "비밀번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (phone.isEmpty()) {
                Toast.makeText(this, "전화번호를 입력해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (!check) {
                Toast.makeText(this, "개인정보 수집 및 이용에 동의해주세요!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            val user = RequestUser(id = null, email = email, name = name, password = password, phone = phone)
            HttpService.getUserService(context = this).store(user).enqueue(object: Callback<UserEntity> {
                override fun onFailure(call: Call<UserEntity>, t: Throwable) {
                    t.message?.let {
                        Log.e(TAG, it)
                    }
                }

                override fun onResponse(call: Call<UserEntity>, response: Response<UserEntity>) {
                    if (response.isSuccessful) {
                        response.body()?.let { body ->
                            body.id?.let {
                                if (it > 0) {
                                    Toast.makeText(baseContext, "회원가입이 완료되었습니다!", Toast.LENGTH_SHORT).show()
                                    finish()
                                }
                            }
                        }
                    }
                }

            })
        }
    }
}