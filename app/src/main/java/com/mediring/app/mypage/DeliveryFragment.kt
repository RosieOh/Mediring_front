package com.mediring.app.mypage

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.mediring.app.R
import com.mediring.app.model.ReviewEntity
import kotlinx.android.synthetic.main.item_review.view.*

class DeliveryFragment : Fragment() {
    private lateinit var rootView: View

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_delivery, container, false)
        return rootView
    }

    companion object {
        const val TAG = "DeliveryFragment"
    }
}