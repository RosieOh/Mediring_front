package com.mediring.app.basket

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.mediring.app.R
import com.mediring.app.helper.EventType
import com.mediring.app.helper.FragmentEventHandler
import kotlinx.android.synthetic.main.fragment_payment.view.*

class PaymentFragment : Fragment() {
    private lateinit var mListener: FragmentEventHandler
    private lateinit var rootView: View

    private var price: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            price = it.getInt("price")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mListener = activity as FragmentEventHandler

        rootView = inflater.inflate(R.layout.fragment_payment, container, false)

        rootView.payment_completed_button.setOnClickListener {
            mListener.buttonClickEventListener(EventType.FRAGMENT_PRODUCT)
        }

        return rootView
    }

    companion object {
        private const val TAG = "PaymentFragment"
    }
}