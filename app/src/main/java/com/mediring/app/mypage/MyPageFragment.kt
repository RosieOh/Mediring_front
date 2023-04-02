package com.mediring.app.mypage

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mediring.app.App
import com.mediring.app.R
import com.mediring.app.helper.EventType
import com.mediring.app.helper.FragmentEventHandler
import com.mediring.app.service.HttpService
import kotlinx.android.synthetic.main.fragment_my_page.view.*

class MyPageFragment : Fragment() {
    companion object {
        const val TAG = "MyPageFragment"
    }

    private lateinit var mListener: FragmentEventHandler
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mListener = activity as FragmentEventHandler

        rootView = inflater.inflate(R.layout.fragment_my_page, container, false)

        rootView.my_page_user_profile_name.text = App.user.name

        rootView.my_page_user_profile_button.setOnClickListener {
            mListener.buttonClickEventListener(EventType.FRAGMENT_USER_PROFILE)
        }

        rootView.my_page_like_product_button.setOnClickListener {
            mListener.buttonClickEventListener(EventType.FRAGMENT_LIKE_PRODUCT)
        }

        rootView.my_page_delivery.setOnClickListener {
            mListener.buttonClickEventListener(EventType.FRAGMENT_DELIVERY)
        }

        return rootView
    }
}