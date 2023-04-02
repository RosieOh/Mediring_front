package com.mediring.app.mypage

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.mediring.app.App
import com.mediring.app.R
import com.mediring.app.databinding.FragmentProductInfoBinding
import com.mediring.app.databinding.FragmentUserProfileBinding
import com.mediring.app.model.AddressEntity
import com.mediring.app.model.UserEntity
import com.mediring.app.model.request.RequestAddress
import com.mediring.app.model.request.RequestUser
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.fragment_user_profile.view.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class UserProfileFragment : Fragment() {
    private var binding: FragmentUserProfileBinding? = null
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        rootView = binding?.root!!

        context?.apply {
            rootView.user_profile_email.setText(App.user.email)
            rootView.user_profile_name.setText(App.user.name)
            rootView.user_profile_phone.setText(App.user.phone)
            App.user.addresses?.let {
                if (it.isNotEmpty()) {
                    rootView.user_profile_address.setText(it[0].postDetail)
                }
            }

            rootView.user_profile_save_button.setOnClickListener {
                val email = "${rootView.user_profile_email.text}"
                val name = "${rootView.user_profile_name.text}"
                val phone = "${rootView.user_profile_phone.text}"
                val address = "${rootView.user_profile_address.text}"

                val client = HttpService.getUserService(this)
                val requestUser = RequestUser(id = App.user.id, name = name, phone = phone, email = email, password = null)
                val requestAddress = RequestAddress(userId = App.user.id!!, address = address)
                client.editAddress(requestAddress).enqueue(object: Callback<AddressEntity> {
                    override fun onFailure(call: Call<AddressEntity>, t: Throwable) {
                        t.message?.let {
                            Log.e(TAG, it)
                        }
                    }

                    override fun onResponse(call: Call<AddressEntity>, response: Response<AddressEntity>) {
                        if (response.isSuccessful) {
                            client.editUser(requestUser).enqueue(object: Callback<UserEntity> {
                                override fun onFailure(call: Call<UserEntity>, t: Throwable) {
                                    t.message?.let {
                                        Log.e(TAG, it)
                                    }
                                }

                                override fun onResponse(call: Call<UserEntity>, response: Response<UserEntity>) {
                                    if (response.isSuccessful) {
                                        response.body()?.let {
                                            App.user = it
                                            Toast.makeText(this@apply, "저장이 완료되었습니다!", Toast.LENGTH_SHORT).show()
                                        }
                                    }
                                }
                            })
                        }
                    }

                })
            }
        }

        return rootView
    }

    companion object {
        private const val TAG = "UserProfileFragment"
    }
}