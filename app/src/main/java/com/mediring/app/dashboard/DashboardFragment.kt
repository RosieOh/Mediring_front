package com.mediring.app.dashboard

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.mediring.app.R
import com.mediring.app.helper.EventType
import com.mediring.app.helper.FragmentEventHandler
import com.mediring.app.main.MainActivity
import com.mediring.app.survey.SurveyActivity
import kotlinx.android.synthetic.main.fragment_dashboard.view.*

class DashboardFragment : Fragment() {
    companion object {
        const val TAG = "DashboardFragment"
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
        mListener = activity as MainActivity

        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false)
        context?.apply {
            rootView.dashboard_survey_button.setOnClickListener {
                startActivity(Intent(this, SurveyActivity::class.java))
            }
        }
        rootView.dashboard_product_button.setOnClickListener {
            mListener.buttonClickEventListener(eventType = EventType.FRAGMENT_PRODUCT)
        }

        rootView.dashboard_compare_button.setOnClickListener {
            mListener.buttonClickEventListener(EventType.FRAGMENT_COMPARE)
        }
        return rootView
    }
}